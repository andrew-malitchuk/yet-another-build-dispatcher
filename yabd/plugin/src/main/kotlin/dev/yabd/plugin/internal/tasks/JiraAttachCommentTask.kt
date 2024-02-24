package dev.yabd.plugin.internal.tasks

import dev.yabd.plugin.internal.config.JiraCommentConfig
import dev.yabd.plugin.internal.usecase.jira.JiraLeaveCommentUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class JiraAttachCommentTask : DefaultTask() {
    init {
        group = "Jira"
        description = "Jira file uploader & comment attach"
    }

    @get:Input
    abstract val jiraCommentConfig: Property<JiraCommentConfig>

    @TaskAction
    @Suppress("ForbiddenComment")
    fun action() {
        with(jiraCommentConfig.get()) {
            logger.apply {
                lifecycle("jira-comment  |   email                          : $email")
                lifecycle("jira-comment  |   jiraCloudInstance              : $jiraCloudInstance")
                lifecycle("jira-comment  |   token                          : $token")
                lifecycle("jira-comment  |   ticket                         : $ticket")
                lifecycle("jira-comment  |   comment                        : $comment")
                val jiraCommentResponse =
                    JiraLeaveCommentUseCase(
                        email = email,
                        token = token,
                        jiraCloudInstance = jiraCloudInstance,
                        ticket = ticket,
                        comment = comment,
                    ).invoke()
                jiraCommentResponse?.let {
                    lifecycle("jira-comment  |   link                        : $it}")
                }
            }
        }
    }
}
