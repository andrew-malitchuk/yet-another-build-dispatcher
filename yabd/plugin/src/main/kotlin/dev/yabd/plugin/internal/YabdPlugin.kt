package dev.yabd.plugin.internal

import com.android.build.gradle.internal.plugins.AppPlugin
import dev.yabd.plugin.common.core.ext.capitalize
import dev.yabd.plugin.internal.YabdExtension.Companion.yabdConfig
import dev.yabd.plugin.internal.tasks.jira.JiraAttachBuildTask
import dev.yabd.plugin.internal.tasks.jira.JiraAttachCommentTask
import dev.yabd.plugin.internal.tasks.jira.JiraUploadTask
import dev.yabd.plugin.internal.tasks.slack.SlackUploadTask
import dev.yabd.plugin.internal.tasks.telegram.TelegramUploadTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class YabdPlugin : Plugin<Project> {
    @Suppress("LongMethod")
    override fun apply(project: Project) {
        with(project) {
            val yabd = yabdConfig()
            if (project.plugins.hasPlugin(AppPlugin::class.java)) {
                val androidExtension =
                    project.extensions.getByType(com.android.build.gradle.AppExtension::class.java)

                androidExtension.applicationVariants.all { variant ->
                    project.tasks.apply {
                        register(
                            "$TELEGRAM_UPLOAD${variant.name.capitalize()}",
                            TelegramUploadTask::class.java,
                        ) {
                            yabd.telegram.apply {
                                tag = variant.name
                                it.group = "telegramUpload"
                                it.description = "Task for ${variant.name} variant"
                                it.telegramConfig.set(this)
                            }
                        }

                        register(
                            "$JIRA_UPLOAD${variant.name.capitalize()}",
                            JiraUploadTask::class.java,
                        ) {
                            yabd.jira.apply {
                                tag = variant.name
                                it.group = "jiraUpload"
                                it.description = "Task for ${variant.name} variant"
                                it.jiraConfig.set(this)
                            }
                        }

                        register(
                            "$JIRA_COMMENT${variant.name.capitalize()}",
                            JiraAttachCommentTask::class.java,
                        ) {
                            yabd.jiraCommentConfig.apply {
                                it.group = "jiraUpload"
                                it.description = "Task for ${variant.name} variant"
                                it.jiraCommentConfig.set(this)
                            }
                        }

                        register(
                            "$JIRA_ATTACH_BUILD${variant.name.capitalize()}",
                            JiraAttachBuildTask::class.java,
                        ) {
                            yabd.jiraAttachBuildConfig.apply {
                                tag = variant.name
                                it.group = "jiraUpload"
                                it.description = "Task for ${variant.name} variant"
                                it.jiraAttachBuildConfig.set(this)
                            }
                        }

                        register(
                            "$SLACK_UPLOAD${variant.name.capitalize()}",
                            SlackUploadTask::class.java,
                        ) {
                            yabd.slackConfig.apply {
                                tag = variant.name
                                it.group = "slackUpload"
                                it.description = "Task for ${variant.name} variant"
                                it.slackConfig.set(this)
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val JIRA_ATTACH_BUILD = "jiraAttachBuild"
        const val JIRA_COMMENT = "jiraComment"
        const val JIRA_UPLOAD = "jiraUpload"
        const val TELEGRAM_UPLOAD = "telegramUpload"
        const val SLACK_UPLOAD = "slackUpload"
    }
}
