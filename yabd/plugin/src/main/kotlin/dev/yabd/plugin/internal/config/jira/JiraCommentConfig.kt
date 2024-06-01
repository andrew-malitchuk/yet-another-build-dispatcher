package dev.yabd.plugin.internal.config.jira

/**
 * Configuration class for attaching build artifacts to Jira issues.
 */
open class JiraCommentConfig {
    /**
     * Email associated with the Jira account.
     */
    var email: String? = null

    /**
     * Token for authentication with Jira.
     */
    var token: String? = null

    /**
     * Your Jira cloud namespace, f.e. `example.atlassian.net`
     */
    var jiraCloudInstance: String? = null

    /**
     * Ticket artifact, f.e. JIRA-0123
     */
    var ticket: String? = null

    /**
     * Optional comment to attach with the build artifact.
     */
    var comment: String? = null
}
