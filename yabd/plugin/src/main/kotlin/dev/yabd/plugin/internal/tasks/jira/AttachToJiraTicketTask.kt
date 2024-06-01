package dev.yabd.plugin.internal.tasks.jira

import dev.yabd.plugin.common.core.file.ArtifactPathFinder.defaultArtifactResolveStrategy
import dev.yabd.plugin.internal.config.jira.JiraAttachBuildConfig
import dev.yabd.plugin.internal.core.model.jira.JiraAuthorization
import dev.yabd.plugin.internal.core.model.jira.JiraCloudInstance
import dev.yabd.plugin.internal.core.model.jira.JiraTicket
import dev.yabd.plugin.internal.core.utils.LoggerUtils.logInfo
import dev.yabd.plugin.internal.usecase.jira.AttachToJiraTicketScenario
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

/**
 * Task for attaching files to a Jira ticket and adding comments.
 */
abstract class AttachToJiraTicketTask : DefaultTask() {
    init {
        group = "Jira"
        description = "Jira file uploader & comment attach"
    }

    /**
     * Configuration for attaching files to a Jira ticket.
     */
    @get:Input
    abstract val jiraAttachBuildConfig: Property<JiraAttachBuildConfig>

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
        with(jiraAttachBuildConfig.get()) {
            val artifactPath = project.defaultArtifactResolveStrategy(filePath, tag)
            if (debugOutput) {
                logger.apply {
                    logInfo()
                    lifecycle("attach-to-jira-ticket    |   email               : $email")
                    lifecycle("attach-to-jira-ticket    |   jiraCloudInstance   : $jiraCloudInstance")
                    lifecycle("attach-to-jira-ticket    |   token               : $token")
                    lifecycle("attach-to-jira-ticket    |   ticket              : $ticket")
                    lifecycle("attach-to-jira-ticket    |   comment             : $comment")
                    lifecycle("attach-to-jira-ticket    |   filePath            : ${artifactPath.value}")
                    artifactName?.let {
                        lifecycle("attach-to-jira-ticket    |   artifactName        : $artifactName")
                    }
                }
            }
            val jiraAttachBuildLinkInCommentResponse =
                AttachToJiraTicketScenario(
                    authorization = JiraAuthorization(email, token),
                    jiraCloudInstance = JiraCloudInstance(jiraCloudInstance),
                    ticket = JiraTicket(ticket),
                    commentPattern = comment,
                    artifactPath = artifactPath,
                    artifactName = artifactName,
                ).invoke()
            if (debugOutput) {
                logger.apply {
                    jiraAttachBuildLinkInCommentResponse?.let {
                        lifecycle("attach-to-jira-ticket    |   link               : ${it.self}}")
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
