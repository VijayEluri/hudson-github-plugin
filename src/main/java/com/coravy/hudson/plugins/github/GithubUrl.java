/*
 * $Id$ 
 */
package com.coravy.hudson.plugins.github;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Stefan Saasen <stefan@coravy.com>
 */
public final class GithubUrl {

    /**
     * Normalizes the github URL.
     * <p>
     * Removes unwanted path elements (e.g. <code>tree/master</code>).
     * 
     * @param input
     * @return URL to the project or null if input is invalid.
     */
    private static String normalize(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        // Strip "tree/..."
        if (url.contains("tree/")) {
            url = url.replaceFirst("tree/.*$", "");
        }
        if (!url.endsWith("/")) {
            url += '/';
        }
        return url;
    }

    private final String baseUrl;

    GithubUrl(final String input) {
        this.baseUrl = normalize(input);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.baseUrl;
    }

    /**
     * 
     * @return
     */
    public String baseUrl() {
        return this.baseUrl;
    }

}
