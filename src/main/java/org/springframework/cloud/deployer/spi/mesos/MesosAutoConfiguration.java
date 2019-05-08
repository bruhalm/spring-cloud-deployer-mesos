/*
 * Copyright 2015-2019 the original author or authors.
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

package org.springframework.cloud.deployer.spi.mesos;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.deployer.spi.app.AppDeployer;
import org.springframework.cloud.deployer.spi.mesos.constraints.ConstraintConverter;
import org.springframework.cloud.deployer.spi.mesos.dcos.DcosClusterProperties;
import org.springframework.cloud.deployer.spi.mesos.marathon.MarathonAppDeployer;
import org.springframework.cloud.deployer.spi.mesos.marathon.MarathonAppDeployerProperties;
import org.springframework.cloud.deployer.spi.mesos.metronome.MetronomeTaskLauncher;
import org.springframework.cloud.deployer.spi.mesos.metronome.MetronomeTaskLauncherProperties;
import org.springframework.cloud.deployer.spi.task.TaskLauncher;
import org.springframework.cloud.mesos.dcos.client.DcosHeadersInterceptor;
import org.springframework.cloud.mesos.metronome.client.Metronome;
import org.springframework.cloud.mesos.metronome.client.MetronomeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

import mesosphere.marathon.client.Marathon;
import mesosphere.marathon.client.MarathonClient;

/**
 * Spring Bean configuration for Mesos {@link MarathonAppDeployer} and {@link MetronomeTaskLauncher}.
 *
 * @author Florian Rosenberg
 * @author Thomas Risberg
 * @author Bruh Alemneh
 */
@Configuration
@EnableConfigurationProperties({
	MarathonAppDeployerProperties.class,
	MetronomeTaskLauncherProperties.class,
	DcosClusterProperties.class})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class MesosAutoConfiguration {

	@Bean
	@RefreshScope
	public Marathon marathon(MarathonAppDeployerProperties marathonProperties, DcosClusterProperties dcosClusterProperties) {
		if (StringUtils.hasText(dcosClusterProperties.getAuthorizationToken())) {
			return MarathonClient.getInstance(marathonProperties.getApiEndpoint(),
					new DcosHeadersInterceptor(dcosClusterProperties.getAuthorizationToken()));
		}
		else {
			return MarathonClient.getInstance(marathonProperties.getApiEndpoint());
		}
	}

	@Bean
	@RefreshScope
	public AppDeployer appDeployer(MarathonAppDeployerProperties marathonProperties, Marathon marathon) {
		return new MarathonAppDeployer(marathonProperties, marathon);
	}

	@Bean
	@RefreshScope
	public Metronome metronome(MetronomeTaskLauncherProperties metronomeProperties, DcosClusterProperties dcosClusterProperties) {
		if (StringUtils.hasText(dcosClusterProperties.getAuthorizationToken())) {
			return MetronomeClient.getInstance(metronomeProperties.getApiEndpoint(),
					new DcosHeadersInterceptor(dcosClusterProperties.getAuthorizationToken()));
		}
		else {
			return MetronomeClient.getInstance(metronomeProperties.getApiEndpoint());
		}
	}

	@Bean
	@RefreshScope
	public TaskLauncher taskDeployer(MetronomeTaskLauncherProperties metronomeProperties, Metronome metronome) {
		return new MetronomeTaskLauncher(metronomeProperties, metronome);
	}

	@Bean
	@ConfigurationPropertiesBinding
	public ConstraintConverter constraintConverter() {
		return new ConstraintConverter();
	}

}