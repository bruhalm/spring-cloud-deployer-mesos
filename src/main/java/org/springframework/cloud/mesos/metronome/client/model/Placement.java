
package org.springframework.cloud.mesos.metronome.client.model;

import java.util.List;

public class Placement {

    /**
     * The array of constraints to place this job.
     * 
     */
    private List<Constraint> constraints = null;

    /**
     * The array of constraints to place this job.
     * 
     */
    public List<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * The array of constraints to place this job.
     * 
     */
    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

}
