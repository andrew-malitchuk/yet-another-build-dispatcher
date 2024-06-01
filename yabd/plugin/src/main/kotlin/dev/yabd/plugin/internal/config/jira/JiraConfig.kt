package dev.yabd.plugin.internal.config.jira

open class JiraConfig {
    /**
     * Email associated with the Jira account.
     */
    var email: String? = null

    /**
     * Token for authentication with Jira.
     */
    var token: String? = null

    /**
     * Defines the path to the build artifact.
     */
    var filePath: String? = null

    /**
     * Your Jira cloud namespace, f.e. `example.atlassian.net`
     */
    var jiraCloudInstance: String? = null

    /**
     * Ticket artifact, f.e. JIRA-0123
     */
    var ticket: String? = null

    /**
     * BuildVariant, f.e. `stageDebug`, `debug`, `release` etc
     */
    var tag: String? = null

    /**
     * Your custom name for uploaded artifact
     */
    var artifactName: String? = null
}
