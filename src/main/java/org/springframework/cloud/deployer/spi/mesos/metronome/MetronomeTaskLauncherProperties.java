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

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.deployer.spi.mesos.constraints.Constraint;

/**
 * Configuration properties for interacting with a Metronome service.
 *
 * @author Bruh Alemneh
 */
@ConfigurationProperties(MetronomeTaskLauncherProperties.PREFIX)
public class MetronomeTaskLauncherProperties {

	/*default*/ static final String PREFIX = "spring.cloud.deployer.mesos.metronome";

	/**
	 * The location of the Metronome REST endpoint.
	 */
	private String apiEndpoint = "http://m1.dcos/service/metronome";

	/**
	 * URIs for artifacts to be downloaded when the task is started.
	 */
	private String[] uris;

	/**
	 * How much memory to allocate per module, can be overridden at deployment time.
	 */
	private double memory = 512.0D;

	/**
	 * How many CPUs to allocate per module, can be overridden at deployment time.
	 */
	private double cpu = 0.5D;

	/**
	 * Number of retries to attempt if a command returns a non-zero status.
	 */
	private Integer retries = 1;

	/**
	 * Environment variables to set for any deployed app container.
	 */
	private String[] environmentVariables = new String[]{};

	/**
	 * Email address for task owner.
	 */
	private String ownerEmail;

	/**
	 * Name of task owner.
	 */
	private String ownerName;

	/**
	 * A set of constraints to apply to any launched task, as a comma separated set of (field operator param?) triplets.
	 */
	private Set<Constraint> constraints = new HashSet<>(0);

	public double getMemory() {
		return memory;
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}

	public double getCpu() {
		return cpu;
	}

	public void setCpu(double cpu) {
		this.cpu = cpu;
	}

	public String getApiEndpoint() {
		return apiEndpoint;
	}

	public void setApiEndpoint(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}

	public String[] getUris() {
		return uris;
	}

	public void setUris(String[] uris) {
		this.uris = uris;
	}

	public String[] getEnvironmentVariables() {
		return environmentVariables;
	}

	public void setEnvironmentVariables(String[] environmentVariables) {
		this.environmentVariables = environmentVariables;
	}

	public Integer getRetries() {
		return retries;
	}

	public void setRetries(Integer retries) {
		this.retries = retries;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Set<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(Set<Constraint> constraints) {
		this.constraints = constraints;
	}
}
