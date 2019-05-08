package org.springframework.cloud.mesos.metronome.client.model;

import java.util.List;

public class JobHistory {
	
	private Integer successCount;
	private Integer failureCount;
	private String 	lastSuccessAt;
	private String lastFailureAt;
	private List<RunHistory> successfulFinishedRuns;
	private List<RunHistory> failedFinishedRuns;
	
	public Integer getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}
	public Integer getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}
	public String getLastSuccessAt() {
		return lastSuccessAt;
	}
	public void setLastSuccessAt(String lastSuccessAt) {
		this.lastSuccessAt = lastSuccessAt;
	}
	public String getLastFailureAt() {
		return lastFailureAt;
	}
	public void setLastFailureAt(String lastFailureAt) {
		this.lastFailureAt = lastFailureAt;
	}
	public List<RunHistory> getSuccessfulFinishedRuns() {
		return successfulFinishedRuns;
	}
	public void setSuccessfulFinishedRuns(List<RunHistory> successfulFinishedRuns) {
		this.successfulFinishedRuns = successfulFinishedRuns;
	}
	public List<RunHistory> getFailedFinishedRuns() {
		return failedFinishedRuns;
	}
	public void setFailedFinishedRuns(List<RunHistory> failedFinishedRuns) {
		this.failedFinishedRuns = failedFinishedRuns;
	}
}
