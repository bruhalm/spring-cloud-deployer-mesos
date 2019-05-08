
package org.springframework.cloud.mesos.metronome.client.model;


public class Artifact {

    /**
     * URI to be fetched by Mesos fetcher module
     * (Required)
     * 
     */
    private String uri;
    /**
     * Set fetched artifact as executable
     * 
     */
    private Boolean executable;
    /**
     * Extract fetched artifact if supported by Mesos fetcher module
     * 
     */
    private Boolean extract;
    /**
     * Cache fetched artifact if supported by Mesos fetcher module
     * 
     */
    private Boolean cache;

    /**
     * URI to be fetched by Mesos fetcher module
     * (Required)
     * 
     */
    public String getUri() {
        return uri;
    }

    /**
     * URI to be fetched by Mesos fetcher module
     * (Required)
     * 
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Set fetched artifact as executable
     * 
     */
    public Boolean getExecutable() {
        return executable;
    }

    /**
     * Set fetched artifact as executable
     * 
     */
    public void setExecutable(Boolean executable) {
        this.executable = executable;
    }

    /**
     * Extract fetched artifact if supported by Mesos fetcher module
     * 
     */
    public Boolean getExtract() {
        return extract;
    }

    /**
     * Extract fetched artifact if supported by Mesos fetcher module
     * 
     */
    public void setExtract(Boolean extract) {
        this.extract = extract;
    }

    /**
     * Cache fetched artifact if supported by Mesos fetcher module
     * 
     */
    public Boolean getCache() {
        return cache;
    }

    /**
     * Cache fetched artifact if supported by Mesos fetcher module
     * 
     */
    public void setCache(Boolean cache) {
        this.cache = cache;
    }

}
