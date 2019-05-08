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

import java.util.List;

import org.springframework.cloud.mesos.metronome.client.model.Job;
import org.springframework.cloud.mesos.metronome.client.model.JobHistory;
import org.springframework.cloud.mesos.metronome.client.model.Schedule;
import org.springframework.cloud.mesos.metronome.client.model.ActiveRun;

import feign.Param;
import feign.RequestLine;

/**
 * Interface defining REST end-points to be used when interacting with Metronome
 *
 * @author Bruh Alemneh
 */
public interface Metronome {

	
	@RequestLine("GET /v0/jobs")
	List<Job> createScheduledJob(Job job) throws MetronomeException;
	
	@RequestLine("GET /v1/jobs")
	List<Job> getJobs() throws MetronomeException;
	
	@RequestLine("GET /v1/jobs/{jobId}")
	List<Job> getJob(@Param("jobId") String jobId) throws MetronomeException;

	@RequestLine("POST /v1/jobs")
	void createJob(Job job) throws MetronomeException;
	
	@RequestLine("PUT /v1/jobs")
	void updateJob(Job job) throws MetronomeException;

	@RequestLine("POST /v1/jobs/{jobId}/runs")
	void startJob(@Param("jobId") String jobId) throws MetronomeException;

	@RequestLine("DELETE /v1/jobs/{jobId}")
	void deleteJob(@Param("jobId") String jobId) throws MetronomeException;

	@RequestLine("POST /v1/jobs/{jobId}/runs/{runId}/actions/stop")
	void stopJob(@Param("jobId") String jobId, @Param("runId") String runId) throws MetronomeException;

	@RequestLine("POST /v1/jobs/{jobId}/schedules")
	void scheduleJob(@Param("jobId") String jobId, Schedule schedule) throws MetronomeException;
	
	@RequestLine("GET /v1/jobs/{jobId}/runs?embed=activeRuns")
	List<ActiveRun> getActiveJobRuns(@Param("jobId") String jobId) throws MetronomeException;
	
	@RequestLine("GET /v1/jobs/{jobId}/runs?embed=history")
	List<JobHistory> getJobHistory(@Param("jobId") String jobId) throws MetronomeException;
	
}
