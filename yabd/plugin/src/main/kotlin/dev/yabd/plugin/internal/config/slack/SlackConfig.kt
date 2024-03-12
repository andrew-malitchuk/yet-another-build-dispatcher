package dev.yabd.plugin.internal.config.slack

/**
 * Configuration class for Slack settings and artifact information.
 */
open class SlackConfig {
    /**
     * Slack channel where the artifact will be posted.
     */
    var channel: String? = null

    /**
     * Token for authentication with Slack.
     */
    var token: String? = null

    /**
     * Defines the path to the build artifact.
     */
    var filePath: String? = null

    /**
     * Build variant, for example, `stageDebug`, `debug`, `release`, etc.
     */
    var tag: String? = null

    /**
     * Custom name for the uploaded artifact.
     */
    var artifactName: String? = null
}
