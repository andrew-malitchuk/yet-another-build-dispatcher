package dev.yabd.plugin.internal

import com.android.build.gradle.internal.plugins.AppPlugin
import dev.yabd.plugin.common.core.ext.capitalize
import dev.yabd.plugin.internal.YabdExtension.Companion.yabdConfig
import dev.yabd.plugin.internal.tasks.JiraAttachCommentTask
import dev.yabd.plugin.internal.tasks.JiraUploadTask
import dev.yabd.plugin.internal.tasks.TelegramUploadTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class YabdPlugin : Plugin<Project> {
    @Suppress("ForbiddenComment")
    override fun apply(project: Project) {
        with(project) {
            val yabd = yabdConfig()
            if (project.plugins.hasPlugin(AppPlugin::class.java)) {
                val androidExtension =
                    project.extensions.getByType(com.android.build.gradle.AppExtension::class.java)

                androidExtension.applicationVariants.all { variant ->

                    // TODO: recode
                    project.tasks.register(
                        "telegramUpload${variant.name.capitalize()}",
                        TelegramUploadTask::class.java,
                    ) {
                        yabd.telegram.apply {
                            tag = variant.name
                            it.group = "telegramUpload"
                            it.description = "Task for ${variant.name} variant"
                            it.telegramConfig.set(this)
                        }
                    }

                    // TODO: recode
                    project.tasks.register(
                        "jiraUpload${variant.name.capitalize()}",
                        JiraUploadTask::class.java,
                    ) {
                        yabd.jira.apply {
                            tag = variant.name
                            it.group = "jiraUpload"
                            it.description = "Task for ${variant.name} variant"
                            it.jiraConfig.set(this)
                        }
                    }

                    // TODO: recode
                    project.tasks.register(
                        "jiraComment${variant.name.capitalize()}",
                        JiraAttachCommentTask::class.java,
                    ) {
                        yabd.jiraCommentConfig.apply {
                            it.group = "jiraUpload"
                            it.description = "Task for ${variant.name} variant"
                            it.jiraCommentConfig.set(this)
                        }
                    }
                }
            }
        }
    }
}
