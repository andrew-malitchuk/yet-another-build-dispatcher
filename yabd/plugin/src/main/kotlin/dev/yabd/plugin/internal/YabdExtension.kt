package dev.yabd.plugin.internal

import dev.yabd.plugin.internal.config.JiraCommentConfig
import dev.yabd.plugin.internal.config.JiraConfig
import dev.yabd.plugin.internal.config.TelegramConfig
import org.gradle.api.Project

open class YabdExtension {
    val telegram = TelegramConfig()

    fun telegram(configure: TelegramConfig.() -> Unit) {
        telegram.configure()
    }

    val jira = JiraConfig()

    fun jira(configure: JiraConfig.() -> Unit) {
        jira.configure()
    }

    val jiraCommentConfig = JiraCommentConfig()

    fun jiraComment(configure: JiraCommentConfig.() -> Unit) {
        jiraCommentConfig.configure()
    }

    companion object {
        internal fun Project.yabdConfig(): YabdExtension = extensions.create("yabd", YabdExtension::class.java)
    }
}
