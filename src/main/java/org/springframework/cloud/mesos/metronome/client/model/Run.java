
package org.springframework.cloud.mesos.metronome.client.model;

import java.util.List;


/**
 * A run specification
 * 
 */
public class Run {

    /**
     * An array of strings that represents an alternative mode of specifying the command to run. This was motivated by safe usage of containerizer features like a custom Docker ENTRYPOINT. Either `cmd` or `args` must be supplied. It is invalid to supply both `cmd` and `args` in the same job.
     * 
     */
    private List<String> args = null;
    /**
     * Provided URIs are passed to Mesos fetcher module and resolved in runtime.
     * 
     */
    private List<Artifact> artifacts = null;
    /**
     * The command that is executed.  This value is wrapped by Mesos via `/bin/sh -c ${job.cmd}`.  Either `cmd` or `args` must be supplied. It is invalid to supply both `cmd` and `args` in the same job.
     * 
     */
    private String cmd;
    /**
     * The number of CPU shares this job needs per instance. This number does not have to be integer, but can be a fraction.
     * (Required)
     * 
     */
    private Double cpus;
    /**
     * How much disk space is needed for this job. This number does not have to be an integer, but can be a fraction.
     * (Required)
     * 
     */
    private Double disk;
    private Docker docker;
    private Env env;
    /**
     * The number of seconds until the job needs to be running. If the deadline is reached without successfully running the job, the job is aborted.
     * 
     */
    private Integer maxLaunchDelay;
    /**
     * The amount of memory in MB that is needed for the job per instance.
     * (Required)
     * 
     */
    private Double mem;
    private Placement placement;
    /**
     * The user to use to run the tasks on the agent.
     * 
     */
    private String user;
    private Restart restart;
    private List<Volume> volumes = null;

    /**
     * An array of strings that represents an alternative mode of specifying the command to run. This was motivated by safe usage of containerizer features like a custom Docker ENTRYPOINT. Either `cmd` or `args` must be supplied. It is invalid to supply both `cmd` and `args` in the same job.
     * 
     */
    public List<String> getArgs() {
        return args;
    }

    /**
     * An array of strings that represents an alternative mode of specifying the command to run. This was motivated by safe usage of containerizer features like a custom Docker ENTRYPOINT. Either `cmd` or `args` must be supplied. It is invalid to supply both `cmd` and `args` in the same job.
     * 
     */
    public void setArgs(List<String> args) {
        this.args = args;
    }

    /**
     * Provided URIs are passed to Mesos fetcher module and resolved in runtime.
     * 
     */
    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    /**
     * Provided URIs are passed to Mesos fetcher module and resolved in runtime.
     * 
     */
    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    /**
     * The command that is executed.  This value is wrapped by Mesos via `/bin/sh -c ${job.cmd}`.  Either `cmd` or `args` must be supplied. It is invalid to supply both `cmd` and `args` in the same job.
     * 
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * The command that is executed.  This value is wrapped by Mesos via `/bin/sh -c ${job.cmd}`.  Either `cmd` or `args` must be supplied. It is invalid to supply both `cmd` and `args` in the same job.
     * 
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * The number of CPU shares this job needs per instance. This number does not have to be integer, but can be a fraction.
     * (Required)
     * 
     */
    public Double getCpus() {
        return cpus;
    }

    /**
     * The number of CPU shares this job needs per instance. This number does not have to be integer, but can be a fraction.
     * (Required)
     * 
     */
    public void setCpus(Double cpus) {
        this.cpus = cpus;
    }

    /**
     * How much disk space is needed for this job. This number does not have to be an integer, but can be a fraction.
     * (Required)
     * 
     */
    public Double getDisk() {
        return disk;
    }

    /**
     * How much disk space is needed for this job. This number does not have to be an integer, but can be a fraction.
     * (Required)
     * 
     */
    public void setDisk(Double disk) {
        this.disk = disk;
    }

    public Docker getDocker() {
        return docker;
    }

    public void setDocker(Docker docker) {
        this.docker = docker;
    }

    public Env getEnv() {
        return env;
    }

    public void setEnv(Env env) {
        this.env = env;
    }

    /**
     * The number of seconds until the job needs to be running. If the deadline is reached without successfully running the job, the job is aborted.
     * 
     */
    public Integer getMaxLaunchDelay() {
        return maxLaunchDelay;
    }

    /**
     * The number of seconds until the job needs to be running. If the deadline is reached without successfully running the job, the job is aborted.
     * 
     */
    public void setMaxLaunchDelay(Integer maxLaunchDelay) {
        this.maxLaunchDelay = maxLaunchDelay;
    }

    /**
     * The amount of memory in MB that is needed for the job per instance.
     * (Required)
     * 
     */
    public Double getMem() {
        return mem;
    }

    /**
     * The amount of memory in MB that is needed for the job per instance.
     * (Required)
     * 
     */
    public void setMem(Double mem) {
        this.mem = mem;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    /**
     * The user to use to run the tasks on the agent.
     * 
     */
    public String getUser() {
        return user;
    }

    /**
     * The user to use to run the tasks on the agent.
     * 
     */
    public void setUser(String user) {
        this.user = user;
    }

    public Restart getRestart() {
        return restart;
    }

    public void setRestart(Restart restart) {
        this.restart = restart;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

}
