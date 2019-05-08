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

package org.springframework.cloud.deployer.spi.mesos.chronos;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.deployer.spi.mesos.dcos.DcosClusterProperties;
import org.springframework.cloud.deployer.spi.mesos.metronome.MetronomeTaskLauncherProperties;
import org.springframework.cloud.deployer.spi.test.junit.AbstractExternalResourceTestSupport;
import org.springframework.cloud.mesos.dcos.client.DcosHeadersInterceptor;
import org.springframework.cloud.mesos.metronome.client.Metronome;
import org.springframework.cloud.mesos.metronome.client.MetronomeClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * JUnit {@link org.junit.Rule} that detects the fact that a Metronome installation is available.
 *
 * @author Bruh Alemneh
 */
public class MetronomeTestSupport extends AbstractExternalResourceTestSupport<Metronome> {

	private ConfigurableApplicationContext context;

	MetronomeTestSupport() {
		super("METRONOME");
	}


	@Override
	protected void cleanupResource() {
		context.close();
	}

	@Override
	protected void obtainResource() throws Exception {
		context = new SpringApplicationBuilder(Config.class).web(WebApplicationType.NONE).run();
		resource = context.getBean(Metronome.class);
	}

	@Configuration
	@EnableAutoConfiguration
	@EnableConfigurationProperties({MetronomeTaskLauncherProperties.class, DcosClusterProperties.class})
	public static class Config {

		@Bean
		public Metronome metronome(MetronomeTaskLauncherProperties taskLauncherProperties,
		                       DcosClusterProperties dcosClusterProperties) {
			if (StringUtils.hasText(dcosClusterProperties.getAuthorizationToken())) {
				return MetronomeClient.getInstance(taskLauncherProperties.getApiEndpoint(),
						new DcosHeadersInterceptor(dcosClusterProperties.getAuthorizationToken()));
			}
			else {
				return MetronomeClient.getInstance(taskLauncherProperties.getApiEndpoint());
			}
		}
	}
}
