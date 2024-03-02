package dev.yabd.plugin.internal

import dev.yabd.plugin.internal.config.jira.JiraAttachBuildConfig
import dev.yabd.plugin.internal.config.jira.JiraCommentConfig
import dev.yabd.plugin.internal.config.jira.JiraConfig
import dev.yabd.plugin.internal.config.slack.SlackConfig
import dev.yabd.plugin.internal.config.telegram.TelegramConfig
import org.gradle.api.Project

open class YabdExtension {
    val telegram = TelegramConfig()
    val jira = JiraConfig()
    val jiraCommentConfig = JiraCommentConfig()
    val jiraAttachBuildConfig = JiraAttachBuildConfig()
    val slackConfig = SlackConfig()

    fun telegram(configure: TelegramConfig.() -> Unit) = telegram.configure()

    fun jira(configure: JiraConfig.() -> Unit) = jira.configure()

    fun jiraComment(configure: JiraCommentConfig.() -> Unit) = jiraCommentConfig.configure()

    fun jiraAttachBuild(configure: JiraAttachBuildConfig.() -> Unit) = jiraAttachBuildConfig.configure()

    fun slackConfig(configure: SlackConfig.() -> Unit) = slackConfig.configure()

    companion object {
        internal fun Project.yabdConfig(): YabdExtension = extensions.create("yabd", YabdExtension::class.java)
    }
}
