
package org.springframework.cloud.mesos.metronome.client.model;

import java.util.HashMap;
import java.util.Map;


/**
 * Attaching metadata to jobs can be useful to expose additional information to other services, so we added the ability to place labels on jobs (for example, you could label jobs staging and production to mark services by their position in the pipeline).
 * 
 */
public class Labels {

    private Map<String, String> additionalProperties = new HashMap<String, String>();

    public Map<String, String> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, String value) {
        this.additionalProperties.put(name, value);
    }

}
