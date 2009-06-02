package com.coravy.hudson.plugins.github;

import hudson.Extension;
import hudson.MarkupText;
import hudson.MarkupText.SubText;
import hudson.model.AbstractBuild;
import hudson.scm.ChangeLogAnnotator;
import hudson.scm.ChangeLogSet.Entry;

import java.util.regex.Pattern;

/**
 * This class annotates changelog links.
 * <p>
 * It's based on the TracLinkAnnotator.
 * 
 * @author Stefan Saasen <stefan@coravy.com>
 */
@Extension
public class GithubLinkAnnotator extends ChangeLogAnnotator {

    @Override
    public void annotate(AbstractBuild<?, ?> build, Entry change,
            MarkupText text) {
        final GithubProjectProperty p = build.getProject().getProperty(
                GithubProjectProperty.class);
        if (null == p || null == p.getProjectUrl()) {
            return;
        }
        annotate(p.getProjectUrl(), text);
    }

    void annotate(final String url, final MarkupText text) {
        for (LinkMarkup markup : MARKUPS) {
            markup.process(text, url);
        }
    }

    private static final class LinkMarkup {
        private final Pattern pattern;
        private final String href;

        LinkMarkup(String pattern, String href) {
            // \\\\d becomes \\d when in the expanded text.
            pattern = NUM_PATTERN.matcher(pattern).replaceAll("(\\\\d+)");
            pattern = ANYWORD_PATTERN.matcher(pattern).replaceAll(
                    "((?:\\\\w|[._-])+)");
            this.pattern = Pattern.compile(pattern);
            this.href = href;
        }

        void process(MarkupText text, String url) {
            for (SubText st : text.findTokens(pattern)) {
                st.surroundWith("<a href='" + url + href + "'>", "</a>");
            }
        }

        private static final Pattern NUM_PATTERN = Pattern.compile("NUM");
        private static final Pattern ANYWORD_PATTERN = Pattern
                .compile("ANYWORD");
    }

    private static final LinkMarkup[] MARKUPS = new LinkMarkup[] { new LinkMarkup(
            "(?:C|c)lose(?:s?)\\s(?<!\\:)(?:#)NUM", // "Closes #123"
            "issues/$1/find") };
}
