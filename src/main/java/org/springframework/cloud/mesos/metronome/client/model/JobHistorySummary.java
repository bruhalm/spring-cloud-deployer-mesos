package org.springframework.cloud.mesos.metronome.client.model;

public class JobHistorySummary {
	
	private Integer successCount;
	private Integer failureCount;
	private String lastSuccessAt;
	private String lastFaiulureAt;
	
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
	public String getLastFaiulureAt() {
		return lastFaiulureAt;
	}
	public void setLastFaiulureAt(String lastFaiulureAt) {
		this.lastFaiulureAt = lastFaiulureAt;
	}
	
}
