/*
 * $Id$ 
 */
package com.coravy.hudson.plugins.github.browser;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.model.Descriptor.FormException;
import hudson.plugins.git.GitChangeSet;
import hudson.plugins.git.browser.GitWeb;
import hudson.scm.RepositoryBrowser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import com.coravy.hudson.plugins.github.GithubProjectProperty;
import com.coravy.hudson.plugins.github.GithubUrl;

/**
 * 
 * 
 * @todo: The git plugin should filter the available browser instance based on
 *        RepositoryBrowser&lt;GitChangeSet&gt; instead of GitWeb. Then this
 *        class would not need to extend {@see GitWeb}.
 * @author Stefan Saasen <stefan@coravy.com>
 */
public class GithubBrowser extends GitWeb {

    private static final long serialVersionUID = -8871764260087653163L;

    @DataBoundConstructor
    public GithubBrowser() throws MalformedURLException {
        super("http://github.com/"); // unnecessary but the parent class
        // requires a valid URL (which we don't
        // use).
    }

    /**
     * Gets a URL for the {@link TracProjectProperty#tracWebsite} value
     * configured for the current project.
     */
    private GithubUrl getGithubUrl(GitChangeSet cs) {
        AbstractProject<?, ?> p = (AbstractProject<?, ?>) cs.getParent().build
                .getProject();
        GithubProjectProperty pp = p.getProperty(GithubProjectProperty.class);
        if (null != pp) {
            return pp.getProjectUrl();
        }
        return null;
    }

    /*
    @Extension
    public static final class DescriptorImpl extends
            Descriptor<RepositoryBrowser<?>> {
        public DescriptorImpl() {
            super(GithubBrowser.class);
            System.out
                    .println("%%%%%%%%%%%%%%%%%% GithubBrowser#DescriptorImpl @@@@@@@@@@@@@@@@@@");
        }

        @Override
        public RepositoryBrowser<?> newInstance(StaplerRequest req,
                JSONObject formData) throws FormException {
            try {
                return new GithubBrowser();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e.getLocalizedMessage(), e);
            }
        }

        public String getDisplayName() {
            return "github";
        }
    }
*/

    @Override
    public URL getChangeSetLink(GitChangeSet changeSet) throws IOException {
        System.out
                .println("%%%%%%%%%%%%%%%%%% GithubBrowser#getChangeSetLink @@@@@@@@@@@@@@@@@@");
        GithubUrl baseUrl = getGithubUrl(changeSet);
        return new URL(baseUrl.commitId(changeSet.getId()));
    }
}
