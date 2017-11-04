package org.jenkinsci.plugins.postbuildscript;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;

public class ProcessorFactory {

    public static Processor create(
        AbstractBuild<?, ?> build,
        Launcher launcher,
        BuildListener listener,
        Configuration config) {
        return new Processor(build, launcher, listener, config);
    }
}
