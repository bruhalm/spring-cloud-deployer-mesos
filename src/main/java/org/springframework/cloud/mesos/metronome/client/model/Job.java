
package org.springframework.cloud.mesos.metronome.client.model;

import java.util.List;

public class Job {

    /**
     * 
     * (Required)
     * 
     */
    private String id;
    /**
     * A description of this job.
     * 
     */
    private String description;
    /**
     * Attaching metadata to jobs can be useful to expose additional information to other services, so we added the ability to place labels on jobs (for example, you could label jobs staging and production to mark services by their position in the pipeline).
     * 
     */
    private Labels labels;
    /**
     * A run specification
     * (Required)
     * 
     */
    private Run run;
    /**
    * All schedules for this job.
    * 
    */
    private List<Schedule> schedules = null;
    
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
     * A description of this job.
     * 
     */
    public String getDescription() {
        return description;
    }

    /**
     * A description of this job.
     * 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Attaching metadata to jobs can be useful to expose additional information to other services, so we added the ability to place labels on jobs (for example, you could label jobs staging and production to mark services by their position in the pipeline).
     * 
     */
    public Labels getLabels() {
        return labels;
    }

    /**
     * Attaching metadata to jobs can be useful to expose additional information to other services, so we added the ability to place labels on jobs (for example, you could label jobs staging and production to mark services by their position in the pipeline).
     * 
     */
    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    /**
     * A run specification
     * (Required)
     * 
     */
    public Run getRun() {
        return run;
    }

    /**
     * A run specification
     * (Required)
     * 
     */
    public void setRun(Run run) {
        this.run = run;
    }

    /**
    * All schedules for this job.
    * 
    */
    public List<Schedule> getSchedules() {
    return schedules;
    }

    /**
    * All schedules for this job.
    * 
    */
    public void setSchedules(List<Schedule> schedules) {
    this.schedules = schedules;
    }
}
