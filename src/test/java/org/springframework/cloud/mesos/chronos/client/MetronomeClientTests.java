package org.springframework.cloud.mesos.chronos.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.cloud.mesos.metronome.client.Metronome;
import org.springframework.cloud.mesos.metronome.client.MetronomeClient;
import org.springframework.cloud.mesos.metronome.client.MetronomeException;
import org.springframework.cloud.mesos.metronome.client.model.Job;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class MetronomeClientTests {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(WireMockSpring.options());

	private Metronome client = MetronomeClient.getInstance("http://localhost:8080");

	@Test
	public void testClientGetJobs() throws MetronomeException {
		stubFor(get(urlEqualTo("/v1/jobs"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody("[\\n  {\\n    \\\"description\\\": \\\"Send summary every day\\\",\\n    \\\"id\\\": \\\"prod.email.summary\\\",\\n    \\\"labels\\\": {},\\n    \\\"run\\\": {\\n      \\\"artifacts\\\": [],\\n      \\\"cmd\\\": \\\"sendSummary\\\",\\n      \\\"cpus\\\": 0.1,\\n      \\\"disk\\\": 0,\\n      \\\"env\\\": {},\\n      \\\"maxLaunchDelay\\\": 3600,\\n      \\\"mem\\\": 32,\\n      \\\"placement\\\": {\\n        \\\"constraints\\\": []\\n      },\\n      \\\"restart\\\": {\\n        \\\"activeDeadlineSeconds\\\": 120,\\n        \\\"policy\\\": \\\"NEVER\\\"\\n      },\\n      \\\"volumes\\\": []\\n    }\\n  },\\n  {\\n    \\\"description\\\": \\\"DB cleaner\\\",\\n    \\\"id\\\": \\\"cleanDb --force\\\",\\n    \\\"labels\\\": {},\\n    \\\"run\\\": {\\n      \\\"artifacts\\\": [],\\n      \\\"cmd\\\": \\\"false\\\",\\n      \\\"cpus\\\": 0.1,\\n      \\\"disk\\\": 0,\\n      \\\"env\\\": {},\\n      \\\"maxLaunchDelay\\\": 3600,\\n      \\\"mem\\\": 32,\\n      \\\"placement\\\": {\\n        \\\"constraints\\\": []\\n      },\\n      \\\"restart\\\": {\\n        \\\"activeDeadlineSeconds\\\": 120,\\n        \\\"policy\\\": \\\"NEVER\\\"\\n      },\\n      \\\"volumes\\\": []\\n    }\\n  }\\n]")));
		List<Job> jobs = client.getJobs();
		assertEquals("Jobs found", 1, jobs.size());
		assertEquals("Job data found", "prod.email.summary", jobs.get(0).getId());
	}

	@Test
	public void testClientAddJob() throws MetronomeException {
		stubFor(post(urlEqualTo("/v1/jobs")).willReturn(aResponse()));
		Job job = new Job();
		job.setId("test");
		job.setDescription("testJob");
		client.createJob(job);
	}


	@Test
	public void testClientStartJob() throws MetronomeException {
		stubFor(post(urlEqualTo("/v1/jobs/test/runs")).willReturn(aResponse()));
		client.startJob("test");
	}

	@Test
	public void testClientDeleteJob() throws MetronomeException {
		stubFor(delete(urlEqualTo("/v1/jobs/test")).willReturn(aResponse()));
		client.deleteJob("test");
	}


}
