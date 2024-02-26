package dev.yabd.plugin.internal

import dev.yabd.plugin.internal.config.JiraAttachBuildConfig
import dev.yabd.plugin.internal.config.JiraCommentConfig
import dev.yabd.plugin.internal.config.JiraConfig
import dev.yabd.plugin.internal.config.TelegramConfig
import org.gradle.api.Project

open class YabdExtension {
    val telegram = TelegramConfig()
    val jira = JiraConfig()
    val jiraCommentConfig = JiraCommentConfig()
    val jiraAttachBuildConfig = JiraAttachBuildConfig()

    fun telegram(configure: TelegramConfig.() -> Unit) {
        telegram.configure()
    }

    fun jira(configure: JiraConfig.() -> Unit) {
        jira.configure()
    }

    fun jiraComment(configure: JiraCommentConfig.() -> Unit) {
        jiraCommentConfig.configure()
    }

    fun jiraAttachBuild(configure: JiraAttachBuildConfig.() -> Unit) {
        jiraAttachBuildConfig.configure()
    }

    companion object {
        internal fun Project.yabdConfig(): YabdExtension = extensions.create("yabd", YabdExtension::class.java)
    }
}
