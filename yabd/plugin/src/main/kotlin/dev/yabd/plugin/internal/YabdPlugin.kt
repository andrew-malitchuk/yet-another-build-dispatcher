package dev.yabd.plugin.internal

import com.android.build.gradle.api.ApplicationVariant
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
import org.gradle.api.tasks.TaskContainer

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
                        configureTelegramUpload(variant, yabd)
                        configureJiraUpload(variant, yabd)
                        configureJiraComment(variant, yabd)
                        configureJiraAttackBuild(variant, yabd)
                        configureSlackUpload(variant, yabd)
                    }
                }
            }
        }
    }

    private fun TaskContainer.configureSlackUpload(
        variant: ApplicationVariant,
        yabd: YabdExtension,
    ) {
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

    private fun TaskContainer.configureJiraAttackBuild(
        variant: ApplicationVariant,
        yabd: YabdExtension,
    ) {
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
    }

    private fun TaskContainer.configureJiraComment(
        variant: ApplicationVariant,
        yabd: YabdExtension,
    ) {
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
                it.group = "jiraUpload"
                it.description = "Task for ${variant.name} variant"
                it.jiraConfig.set(this)
            }
        }
    }

    private fun TaskContainer.configureTelegramUpload(
        variant: ApplicationVariant,
        yabd: YabdExtension,
    ) {
        register(
            "$TELEGRAM_UPLOAD${variant.name.capitalize()}",
            TelegramUploadTask::class.java,
        ) {
            it.foobar = it.project.findProperty("foobar") as? Boolean ?: false
            yabd.telegram.apply {
                tag = variant.name
                it.group = "telegramUpload"
                it.description = "Task for ${variant.name} variant"
                it.telegramConfig.set(this)
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
