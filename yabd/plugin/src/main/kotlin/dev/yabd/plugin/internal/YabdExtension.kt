package dev.yabd.plugin.internal

import dev.yabd.plugin.internal.config.jira.JiraAttachBuildConfig
import dev.yabd.plugin.internal.config.jira.JiraCommentConfig
import dev.yabd.plugin.internal.config.jira.JiraConfig
import dev.yabd.plugin.internal.config.slack.SlackConfig
import dev.yabd.plugin.internal.config.telegram.TelegramConfig
import org.gradle.api.Project

/**
 * Configuration extension class for Yabd plugin, allowing customization of various integration settings.
 */
open class YabdExtension {
    /**
     * Configuration for Telegram settings.
     */
    val telegram = TelegramConfig()

    /**
     * Configuration for Jira settings.
     */
    val jira = JiraConfig()

    /**
     * Configuration for Jira comment settings.
     */
    val jiraCommentConfig = JiraCommentConfig()

    /**
     * Configuration for attaching build artifacts to Jira tickets.
     */
    val jiraAttachBuildConfig = JiraAttachBuildConfig()

    /**
     * Configuration for Slack settings.
     */
    val slackConfig = SlackConfig()

    /**
     * Customizes the Telegram configuration.
     * @param configure Lambda function to configure Telegram settings.
     */
    fun telegram(configure: TelegramConfig.() -> Unit) = telegram.configure()

    /**
     * Customizes the Jira configuration.
     * @param configure Lambda function to configure Jira settings.
     */
    fun jira(configure: JiraConfig.() -> Unit) = jira.configure()

    /**
     * Customizes the Jira comment configuration.
     * @param configure Lambda function to configure Jira comment settings.
     */
    fun jiraComment(configure: JiraCommentConfig.() -> Unit) = jiraCommentConfig.configure()

    /**
     * Customizes the Jira attachment build configuration.
     * @param configure Lambda function to configure Jira attachment build settings.
     */
    fun jiraAttachBuild(configure: JiraAttachBuildConfig.() -> Unit) = jiraAttachBuildConfig.configure()

    /**
     * Customizes the Slack configuration.
     * @param configure Lambda function to configure Slack settings.
     */
    fun slackConfig(configure: SlackConfig.() -> Unit) = slackConfig.configure()

    companion object {
        /**
         * Creates and returns a YabdExtension instance for the project.
         * @receiver The project instance.
         * @return The created YabdExtension instance.
         */
        internal fun Project.yabdConfig(): YabdExtension = extensions.create("yabd", YabdExtension::class.java)
    }
}
