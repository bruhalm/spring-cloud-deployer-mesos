
package org.springframework.cloud.mesos.metronome.client.model;


public class Docker {

    /**
     * 
     * (Required)
     * 
     */
    private String image;

    /**
     * 
     * (Required)
     * 
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setImage(String image) {
        this.image = image;
    }

}
