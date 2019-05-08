
package org.springframework.cloud.mesos.metronome.client.model;

import java.util.HashMap;
import java.util.Map;

public class Volume {

    /**
     * The path of the volume in the container
     * (Required)
     * 
     */
    private String containerPath;
    /**
     * The path of the volume on the host
     * (Required)
     * 
     */
    private String hostPath;
    /**
     * Possible values are RO for ReadOnly and RW for Read/Write
     * (Required)
     * 
     */
    private Volume.Mode mode;

    /**
     * The path of the volume in the container
     * (Required)
     * 
     */
    public String getContainerPath() {
        return containerPath;
    }

    /**
     * The path of the volume in the container
     * (Required)
     * 
     */
    public void setContainerPath(String containerPath) {
        this.containerPath = containerPath;
    }

    /**
     * The path of the volume on the host
     * (Required)
     * 
     */
    public String getHostPath() {
        return hostPath;
    }

    /**
     * The path of the volume on the host
     * (Required)
     * 
     */
    public void setHostPath(String hostPath) {
        this.hostPath = hostPath;
    }

    /**
     * Possible values are RO for ReadOnly and RW for Read/Write
     * (Required)
     * 
     */
    public Volume.Mode getMode() {
        return mode;
    }

    /**
     * Possible values are RO for ReadOnly and RW for Read/Write
     * (Required)
     * 
     */
    public void setMode(Volume.Mode mode) {
        this.mode = mode;
    }

    public enum Mode {

        RO("RO"),
        RW("RW");
        private final String value;
        private final static Map<String, Volume.Mode> CONSTANTS = new HashMap<String, Volume.Mode>();

        static {
            for (Volume.Mode c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Mode(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Volume.Mode fromValue(String value) {
            Volume.Mode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
