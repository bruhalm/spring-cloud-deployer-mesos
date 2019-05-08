package org.springframework.cloud.mesos.metronome.client.model;

import java.util.ArrayList;
import java.util.List;

public class ActiveRun {
	
	private String completedAt;
	private String createdAt;
	private String id;
	private String jobId;
	private String status;
	private List<ActiveTask> tasks = new ArrayList<>();
	
	public String getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(String completedAt) {
		this.completedAt = completedAt;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<ActiveTask> getTasks() {
		return tasks;
	}
	public void setTasks(List<ActiveTask> tasks) {
		this.tasks = tasks;
	}

}