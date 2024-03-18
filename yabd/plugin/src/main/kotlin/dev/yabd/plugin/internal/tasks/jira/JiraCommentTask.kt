package dev.yabd.plugin.internal.tasks.jira

import dev.yabd.plugin.internal.config.jira.JiraCommentConfig
import dev.yabd.plugin.internal.core.model.jira.JiraAuthorization
import dev.yabd.plugin.internal.core.model.jira.JiraCloudInstance
import dev.yabd.plugin.internal.core.model.jira.JiraTicket
import dev.yabd.plugin.internal.usecase.jira.JiraCommentUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

/**
 * Task for commenting on a Jira ticket.
 */
abstract class JiraCommentTask : DefaultTask() {

    init {
        group = "Jira"
        description = "Jira file uploader & comment attach"
    }

    /**
     * Configuration for commenting on a Jira ticket.
     */
    @get:Input
    abstract val jiraCommentConfig: Property<JiraCommentConfig>

    /**
     * Boolean option to enable debug output.
     */
    @Option(description = DEBUG_DESCRIPTION, option = DEBUG_OUTPUT)
    @get:Input
    var debugOutput: Boolean = false

    /**
     * Action method for the task.
     */
    @Suppress("NestedBlockDepth")
    @TaskAction
    fun action() {
        with(jiraCommentConfig.get()) {
            if (debugOutput) {
                logger.apply {
                    lifecycle("jira-comment  |   email                          : $email")
                    lifecycle("jira-comment  |   jiraCloudInstance              : $jiraCloudInstance")
                    lifecycle("jira-comment  |   token                          : $token")
                    lifecycle("jira-comment  |   ticket                         : $ticket")
                    lifecycle("jira-comment  |   comment                        : $comment")
                }
            }
            val jiraCommentResponse =
                JiraCommentUseCase(
                    authorization = JiraAuthorization(email, token),
                    jiraCloudInstance = JiraCloudInstance(jiraCloudInstance),
                    ticket = JiraTicket(ticket),
                    comment = comment,
                ).invoke()
            if (debugOutput) {
                logger.apply {
                    jiraCommentResponse?.let {
                        lifecycle("jira-comment  |   link                           : ${it.self}}")
                    }
                }
            }
        }
    }

    companion object {
        const val DEBUG_OUTPUT = "debugOutput"
        const val DEBUG_DESCRIPTION = "debugOutput"
    }
}
