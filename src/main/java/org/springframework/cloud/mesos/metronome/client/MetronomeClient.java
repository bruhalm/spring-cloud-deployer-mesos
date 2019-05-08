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

package org.springframework.cloud.mesos.metronome.client;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import org.springframework.cloud.mesos.metronome.client.model.AbstractModel;
import org.springframework.util.MimeTypeUtils;

import feign.Feign;
import feign.Feign.Builder;
import feign.FeignException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * Class for creating a Feign client for use with Metronome. Based on similar class
 * used with Marathon - {@link mesosphere.marathon.client.Marathon}
 *
 * @author Bruh Alemneh
 */
public class MetronomeClient {

	static class MetronomeHeadersInterceptor implements RequestInterceptor {
		@Override
		public void apply(RequestTemplate template) {
			template.header("Content-Type", "application/json");
		}
	}
	
	static class MetronomeErrorDecoder implements ErrorDecoder {
		@Override
		public Exception decode(String methodKey, Response response) {
			return new MetronomeException(response.status(), response.reason());
		}
	}
	
	public static Metronome getInstance(String endpoint) {
		return getInstance(endpoint, null);
	}

	/**
	 * The generalized version of the method that allows more in-depth customizations via
	 * {@link RequestInterceptor}s.
	 *
	 * @param endpoint URL for Chronos API
	 */
	public static Metronome getInstance(String endpoint, RequestInterceptor... interceptors) {
		Builder b = Feign.builder()
				.encoder(new GsonEncoder(AbstractModel.GSON))
				.decoder(new MultiDecoder())
				.errorDecoder(new MetronomeErrorDecoder());
		if (interceptors != null) {
			b.requestInterceptors(asList(interceptors));
		}
		b.requestInterceptor(new MetronomeHeadersInterceptor());
		return b.target(Metronome.class, endpoint);
	}

	/**
	 * Creates a Metronome client proxy that performs HTTP basic authentication.
	 */
	public static Metronome getInstanceWithBasicAuth(String endpoint, String username, String password) {
		return getInstance(endpoint, new BasicAuthRequestInterceptor(username, password));
	}

	public static class MultiDecoder implements Decoder {

		GsonDecoder gsonDecoder = new GsonDecoder(AbstractModel.GSON);

		Decoder defaultDecoder = new Default();

		@Override
		public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
			Collection<String> contentTypes = response.headers().get("Content-Type");
			if (contentTypes.contains(MimeTypeUtils.TEXT_PLAIN.toString())) {
				return defaultDecoder.decode(response, type);
			}
			else {
				return gsonDecoder.decode(response, type);
			}
		}
	}
}
