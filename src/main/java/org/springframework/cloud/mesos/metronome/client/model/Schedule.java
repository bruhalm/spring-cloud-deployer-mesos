
package org.springframework.cloud.mesos.metronome.client.model;

import java.util.HashMap;
import java.util.Map;

public class Schedule {

    /**
     * 
     * (Required)
     * 
     */
    private String id;
    /**
     * Cron based schedule string.
     * (Required)
     * 
     */
    private String cron;
    /**
     * IANA based time zone string. See http://www.iana.org/time-zones for a list of available time zones.
     * 
     */
    private String timezone;
    /**
     * The number of seconds until the job is still considered valid to start.
     * 
     */
    private Integer startingDeadlineSeconds;
    /**
     * Defines the behavior if a job is started, before the current job has finished. ALLOW will launch a new job, even if there is an existing run.
     * 
     */
    private Schedule.ConcurrencyPolicy concurrencyPolicy;
    /**
     * Defines if the schedule is enabled or not.
     * 
     */
    private Boolean enabled;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Cron based schedule string.
     * (Required)
     * 
     */
    public String getCron() {
        return cron;
    }

    /**
     * Cron based schedule string.
     * (Required)
     * 
     */
    public void setCron(String cron) {
        this.cron = cron;
    }

    /**
     * IANA based time zone string. See http://www.iana.org/time-zones for a list of available time zones.
     * 
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * IANA based time zone string. See http://www.iana.org/time-zones for a list of available time zones.
     * 
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * The number of seconds until the job is still considered valid to start.
     * 
     */
    public Integer getStartingDeadlineSeconds() {
        return startingDeadlineSeconds;
    }

    /**
     * The number of seconds until the job is still considered valid to start.
     * 
     */
    public void setStartingDeadlineSeconds(Integer startingDeadlineSeconds) {
        this.startingDeadlineSeconds = startingDeadlineSeconds;
    }

    /**
     * Defines the behavior if a job is started, before the current job has finished. ALLOW will launch a new job, even if there is an existing run.
     * 
     */
    public Schedule.ConcurrencyPolicy getConcurrencyPolicy() {
        return concurrencyPolicy;
    }

    /**
     * Defines the behavior if a job is started, before the current job has finished. ALLOW will launch a new job, even if there is an existing run.
     * 
     */
    public void setConcurrencyPolicy(Schedule.ConcurrencyPolicy concurrencyPolicy) {
        this.concurrencyPolicy = concurrencyPolicy;
    }

    /**
     * Defines if the schedule is enabled or not.
     * 
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Defines if the schedule is enabled or not.
     * 
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public enum ConcurrencyPolicy {

        ALLOW("ALLOW");
        private final String value;
        private final static Map<String, Schedule.ConcurrencyPolicy> CONSTANTS = new HashMap<String, Schedule.ConcurrencyPolicy>();

        static {
            for (Schedule.ConcurrencyPolicy c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private ConcurrencyPolicy(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Schedule.ConcurrencyPolicy fromValue(String value) {
            Schedule.ConcurrencyPolicy constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
