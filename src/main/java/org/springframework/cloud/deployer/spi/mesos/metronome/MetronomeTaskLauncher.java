/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.deployer.spi.mesos.metronome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hashids.Hashids;
import org.springframework.cloud.deployer.spi.app.AppDeployer;
import org.springframework.cloud.deployer.spi.core.AppDeploymentRequest;
import org.springframework.cloud.deployer.spi.core.RuntimeEnvironmentInfo;
import org.springframework.cloud.deployer.spi.task.LaunchState;
import org.springframework.cloud.deployer.spi.task.TaskLauncher;
import org.springframework.cloud.deployer.spi.task.TaskStatus;
import org.springframework.cloud.deployer.spi.util.RuntimeVersionUtils;
import org.springframework.cloud.mesos.metronome.client.Metronome;
import org.springframework.cloud.mesos.metronome.client.MetronomeException;
import org.springframework.cloud.mesos.metronome.client.model.ActiveRun;
import org.springframework.cloud.mesos.metronome.client.model.Artifact;
import org.springframework.cloud.mesos.metronome.client.model.Constraint;
import org.springframework.cloud.mesos.metronome.client.model.Docker;
import org.springframework.cloud.mesos.metronome.client.model.Env;
import org.springframework.cloud.mesos.metronome.client.model.Job;
import org.springframework.cloud.mesos.metronome.client.model.Placement;
import org.springframework.cloud.mesos.metronome.client.model.Restart;
import org.springframework.cloud.mesos.metronome.client.model.Restart.Policy;
import org.springframework.cloud.mesos.metronome.client.model.Run;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A task launcher that targets Mesos Metronome.
 *
 * @author Bruh Alemneh
 */
public class MetronomeTaskLauncher implements TaskLauncher {

	private static final Log logger = LogFactory.getLog(MetronomeTaskLauncher.class);

	private MetronomeTaskLauncherProperties properties = new MetronomeTaskLauncherProperties();

	private Metronome metronome;

	public MetronomeTaskLauncher(MetronomeTaskLauncherProperties properties, Metronome metronome) {
		this.properties = properties;
		this.metronome = metronome;
	}

	@Override
	public String launch(AppDeploymentRequest request) {
		String jobId = createDeploymentId(request);
		String image = null;
		try {
			image = request.getResource().getURI().getSchemeSpecificPart();
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to get URI for " + request.getResource(), e);
		}
		logger.info("Using Docker image: " + image);
		
		Job job = new Job();
		job.setId(jobId);
		Run run = new Run();
		job.setRun(run);
		Env env = new Env();
		env.getAdditionalProperties().putAll(createSpringApplicationJson(request));
		run.setEnv(env);
		run.setArgs(createCommandArgs(request));
		if(image != null) {
			Docker docker = new Docker();
			docker.setImage(image);
			run.setDocker(docker);
		}
		Restart restart = new Restart();
		restart.setPolicy(Policy.NEVER);
		run.setRestart(restart);
		Double cpus = deduceCpus(request);
		Double memory = deduceMemory(request);
		run.setCpus(cpus);
		run.setMem(memory);
		run.setPlacement(deduceConstraints(request));

		if (StringUtils.hasText(properties.getOwnerName())) {
			run.setUser(properties.getOwnerName());
		}
		String[] uris = properties.getUris();
		if (uris != null) {
			List<Artifact> artifacts = new ArrayList<>();
			for (int i = 0; i < uris.length; i++) {
				Artifact artifact = new Artifact();
				artifact.setUri(uris[i]);
				artifact.setExecutable(true);
				artifacts.add(artifact);
			}
			run.setArtifacts(artifacts);
		}
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Launching Job with definition:\n" + job.toString());
			}
			metronome.createJob(job);
		} catch (MetronomeException e) {
			logger.error(e.getMessage(), e);
			throw new IllegalStateException(String.format("Error while creating job '%s'", jobId), e);
		}
		return jobId;
	}

	@Override
	public void cancel(String jobId) {
		try {
			List<ActiveRun> activeRuns = metronome.getActiveJobRuns(jobId);
			for(ActiveRun activeRun : activeRuns) {
				String runId = activeRun.getId();
				metronome.stopJob(jobId, runId);
			}
		} catch (MetronomeException e) {
			throw new IllegalStateException(String.format("Error while canceling job '%s'", jobId), e);
		}
	}

	@Override
	public TaskStatus status(String id) {
		
		List<ActiveRun> activeRuns;
		try {
			activeRuns = metronome.getActiveJobRuns(id);
		} catch (MetronomeException e) {
			return new TaskStatus(id, LaunchState.unknown, new HashMap<>());
		}
		if(activeRuns.size() > 0) {
			ActiveRun activeRun = activeRuns.get(activeRuns.size() - 1);
			String status = activeRun.getStatus();
			if("ACTIVE".equalsIgnoreCase(status))
				return new TaskStatus(id, LaunchState.running, new HashMap<>());
			if("STARTING".equalsIgnoreCase(status))
				return new TaskStatus(id, LaunchState.launching, new HashMap<>());
			if("SUCCESS".equalsIgnoreCase(status))
				return new TaskStatus(id, LaunchState.complete, new HashMap<>());
			else {
				return new TaskStatus(id, LaunchState.failed, new HashMap<>());
			}
		}else {
			return new TaskStatus(id, LaunchState.unknown, new HashMap<>());
		}

	}

	@Override
	public void cleanup(String id) {
		try {
			metronome.deleteJob(id);
		}  catch (MetronomeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void destroy(String taskName) {
	}

	@Override
	public RuntimeEnvironmentInfo environmentInfo() {
		String apiVersion = "v1";
		String hostVersion = "unknown";
		return new RuntimeEnvironmentInfo.Builder()
				.spiClass(AppDeployer.class)
				.implementationName(this.getClass().getSimpleName())
				.implementationVersion(RuntimeVersionUtils.getVersion(this.getClass()))
				.platformType("Mesos")
				.platformApiVersion(apiVersion)
				.platformClientVersion(RuntimeVersionUtils.getVersion(metronome.getClass()))
				.platformHostVersion(hostVersion)
				.build();
	}


	protected String createDeploymentId(AppDeploymentRequest request) {
		String name = request.getDefinition().getName();
		Hashids hashids = new Hashids(name);
		String hashid = hashids.encode(System.currentTimeMillis());
		return name + "-" + hashid;
	}

	protected Map<String, Object> createSpringApplicationJson(AppDeploymentRequest request) {
		String value = "{}";
		try {
			value = new ObjectMapper().writeValueAsString(
					Optional.ofNullable(request.getDefinition().getProperties())
							.orElse(Collections.emptyMap()));
		} catch (JsonProcessingException e) {}
		Map<String, Object> springApp = new HashMap<>();
		if (!"{}".equals(value)) {
			springApp.put("name", "SPRING_APPLICATION_JSON");
			springApp.put("value", value);
		}
		return springApp;
	}

	protected List<String> createCommandArgs(AppDeploymentRequest request) {
		List<String> cmdArgs = new LinkedList<String>();
		// add provided command line args
		cmdArgs.addAll(request.getCommandlineArguments());
		logger.debug("Using command args: " + cmdArgs);
		return cmdArgs;
	}

	private Double deduceMemory(AppDeploymentRequest request) {
		String override = request.getDeploymentProperties().get(AppDeployer.MEMORY_PROPERTY_KEY);
		return override != null ? Double.valueOf(override) : properties.getMemory();
	}

	private Double deduceCpus(AppDeploymentRequest request) {
		String override = request.getDeploymentProperties().get(AppDeployer.CPU_PROPERTY_KEY);
		return override != null ? Double.valueOf(override) : properties.getCpu();
	}

	private Placement deduceConstraints(AppDeploymentRequest request) {
		List<Constraint> constraints = StringUtils.commaDelimitedListToSet(request.getDeploymentProperties().get(prefix("constraints")))
			.stream().map(Constraint::new).collect(Collectors.toList());
		Placement placement = new  Placement ();
		placement.setConstraints(constraints);
		return placement;
	}

	private String prefix(String property) {
		return MetronomeTaskLauncherProperties.PREFIX + "." + property;
	}
}
