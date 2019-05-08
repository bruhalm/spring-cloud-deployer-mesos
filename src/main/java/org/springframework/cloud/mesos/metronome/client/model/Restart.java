
package org.springframework.cloud.mesos.metronome.client.model;

import java.util.HashMap;
import java.util.Map;

public class Restart {

    /**
     * 
     * (Required)
     * 
     */
    private Restart.Policy policy;
    private Integer activeDeadlineSeconds;

    /**
     * 
     * (Required)
     * 
     */
    public Restart.Policy getPolicy() {
        return policy;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setPolicy(Restart.Policy policy) {
        this.policy = policy;
    }

    public Integer getActiveDeadlineSeconds() {
        return activeDeadlineSeconds;
    }

    public void setActiveDeadlineSeconds(Integer activeDeadlineSeconds) {
        this.activeDeadlineSeconds = activeDeadlineSeconds;
    }

    public enum Policy {

        NEVER("NEVER"),
        ON_FAILURE("ON_FAILURE");
        private final String value;
        private final static Map<String, Restart.Policy> CONSTANTS = new HashMap<String, Restart.Policy>();

        static {
            for (Restart.Policy c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Policy(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Restart.Policy fromValue(String value) {
            Restart.Policy constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
