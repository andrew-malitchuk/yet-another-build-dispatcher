package dev.yabd.plugin.internal

import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.internal.plugins.AppPlugin
import dev.yabd.plugin.common.core.ext.capitalize
import dev.yabd.plugin.internal.YabdExtension.Companion.yabdConfig
import dev.yabd.plugin.internal.tasks.jira.AttachToJiraTicketTask
import dev.yabd.plugin.internal.tasks.jira.JiraCommentTask
import dev.yabd.plugin.internal.tasks.jira.JiraUploadTask
import dev.yabd.plugin.internal.tasks.slack.ShareOnSlackTask
import dev.yabd.plugin.internal.tasks.slack.SlackCommentTask
import dev.yabd.plugin.internal.tasks.telegram.SendToTelegramTask
import dev.yabd.plugin.internal.tasks.telegram.TelegramCommentTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer

/**
 * A Gradle plugin for configuring tasks
 */
class YabdPlugin : Plugin<Project> {
    /**
     * Applies the Yabd plugin to the project.
     */
    @Suppress("LongMethod")
    override fun apply(project: Project) {
        with(project) {
            val yabd = yabdConfig()
            if (project.plugins.hasPlugin(AppPlugin::class.java)) {
                val androidExtension =
                    project.extensions.getByType(com.android.build.gradle.AppExtension::class.java)

                androidExtension.applicationVariants.all { variant ->
                    project.tasks.apply {
                        configureSendToTelegramTask(variant, yabd)
                        configureTelegramCommentTask(variant, yabd)
                        configureJiraUpload(variant, yabd)
                        configureJiraComment(variant, yabd)
                        configureAttachToJiraTicketTask(variant, yabd)
                        configureShareOnSlack(variant, yabd)
                        configureSlackComment(variant, yabd)
                    }
                }
            }
        }
    }

    private fun TaskContainer.configureSendToTelegramTask(
        variant: ApplicationVariant,
        yabd: YabdExtension,
    ) {
        register(
            "$SEND_TO_TELEGRAM_TASK${variant.name.capitalize()}",
            SendToTelegramTask::class.java,
        ) {
            yabd.telegram.apply {
                tag = variant.name
                it.group = TELEGRAM_GROUP
                it.description = "$SEND_TO_TELEGRAM_TASK    |   ${variant.name}"
                it.telegramConfig.set(this)
            }
        }
    }

    private fun TaskContainer.configureTelegramCommentTask(
        variant: ApplicationVariant,
        yabd: YabdExtension,
    ) {
        register(
            "$TELEGRAM_COMMENT_TASK${variant.name.capitalize()}",
            TelegramCommentTask::class.java,
        ) {
            yabd.telegram.apply {
                tag = variant.name
                it.group = TELEGRAM_GROUP
                it.description = "$TELEGRAM_COMMENT_TASK    |   ${variant.name}"
                it.telegramConfig.set(this)
            }
        }
    }

    private fun TaskContainer.configureJiraUpload(
        variant: ApplicationVariant,
        yabd: YabdExtension,
    ) {
        register(
            "$JIRA_UPLOAD${variant.name.capitalize()}",
            JiraUploadTask::class.java,
        ) {
            yabd.jira.apply {
                tag = variant.name
                it.group = JIRA_GROUP
                it.description = "$JIRA_UPLOAD  |   ${variant.name}"
                it.jiraConfig.set(this)
            }
        }
    }

    private fun TaskContainer.configureJiraComment(
        variant: ApplicationVariant,
        yabd: YabdExtension,
    ) {
        register(
            "$JIRA_COMMENT${variant.name.capitalize()}",
            JiraCommentTask::class.java,
        ) {
            yabd.jiraCommentConfig.apply {
                it.group = JIRA_GROUP
                it.description = "$JIRA_COMMENT |   ${variant.name}"
                it.jiraCommentConfig.set(this)
            }
        }
    }

    private fun TaskContainer.configureAttachToJiraTicketTask(
        variant: ApplicationVariant,
        yabd: YabdExtension,
    ) {
        register(
            "$ATTACH_TO_JIRA_TICKET${variant.name.capitalize()}",
            AttachToJiraTicketTask::class.java,
        ) {
            yabd.jiraAttachBuildConfig.apply {
                tag = variant.name
                it.group = JIRA_GROUP
                it.description = "$ATTACH_TO_JIRA_TICKET    |   ${variant.name}"
                it.jiraAttachBuildConfig.set(this)
            }
        }
    }

    private fun TaskContainer.configureSlackComment(
        variant: ApplicationVariant,
        yabd: YabdExtension,
    ) {
        register(
            "$SLACK_COMMENT${variant.name.capitalize()}",
            SlackCommentTask::class.java,
        ) {
            yabd.slackConfig.apply {
                tag = variant.name
                it.group = SLACK_GROUP
                it.description = "$SLACK_COMMENT    |   ${variant.name}"
                it.slackConfig.set(this)
            }
        }
    }

    private fun TaskContainer.configureShareOnSlack(
        variant: ApplicationVariant,
        yabd: YabdExtension,
    ) {
        register(
            "$SHARE_ON_SLACK${variant.name.capitalize()}",
            ShareOnSlackTask::class.java,
        ) {
            yabd.slackConfig.apply {
                tag = variant.name
                it.group = SLACK_GROUP
                it.description = "$SHARE_ON_SLACK   |   ${variant.name}"
                it.slackConfig.set(this)
            }
        }
    }

    companion object {
        const val SEND_TO_TELEGRAM_TASK = "sendToTelegramTask"
        const val TELEGRAM_COMMENT_TASK = "telegramCommentTask"

        const val JIRA_UPLOAD = "jiraUpload"
        const val JIRA_COMMENT = "jiraComment"
        const val ATTACH_TO_JIRA_TICKET = "attachToJiraTicket"

        const val SLACK_COMMENT = "slackComment"
        const val SHARE_ON_SLACK = "shareOnSlack"

        const val TELEGRAM_GROUP = "Telegram"
        const val JIRA_GROUP = "Jira"
        const val SLACK_GROUP = "Slack"
    }
}
