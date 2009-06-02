/*
 * $Id$ 
 */
package com.coravy.hudson.plugins.github;

import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Job;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 * 
 * @author Stefan Saasen <stefan@coravy.com>
 */
public final class GithubProjectProperty extends
        JobProperty<AbstractProject<?, ?>> {

    /**
     * This will the URL to the project main branch.
     */
    private final String projectUrl;

    @DataBoundConstructor
    public GithubProjectProperty(String projectUrl) {
        if (StringUtils.isBlank(projectUrl)) {
            projectUrl = null;
        } else if (!projectUrl.endsWith("/")) {
            projectUrl += '/';
        }
        this.projectUrl = projectUrl;
    }

    /**
     * @return the projectUrl
     */
    public String getProjectUrl() {
        return projectUrl;
    }

    @Override
    public Action getJobAction(AbstractProject<?, ?> job) {
        if (null != projectUrl) {
            return new GithubLinkAction(this);
        }
        return null;
    }

    /*
     * @Override public Action getJobAction(Job<?, ?> job) { return new
     * GithubLinkAction((AbstractProject) job);//?? }
     */

    @Override
    public JobPropertyDescriptor getDescriptor() {
        return DESCRIPTOR;
    }

    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    public static final class DescriptorImpl extends JobPropertyDescriptor {

        public DescriptorImpl() {
            super(GithubProjectProperty.class);
            load();
        }

        public boolean isApplicable(Class<? extends Job> jobType) {
            return AbstractProject.class.isAssignableFrom(jobType);
        }

        public String getDisplayName() {
            return "Github project page";
        }

        @Override
        public JobProperty<?> newInstance(StaplerRequest req)
                throws FormException {
            try {
                String projectUrl = req.getParameter("github.projectUrl");
                if (null != projectUrl) {
                    return new GithubProjectProperty(projectUrl);
                }
                return null;
            } catch (IllegalArgumentException e) {
                throw new FormException("github.githubPage",
                        "github.githubPage");
            }
        }

    }

}
