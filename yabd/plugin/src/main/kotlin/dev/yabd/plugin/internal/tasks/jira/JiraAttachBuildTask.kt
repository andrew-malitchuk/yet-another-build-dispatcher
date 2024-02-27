package dev.yabd.plugin.internal.tasks.jira

import dev.yabd.plugin.common.core.file.ArtifactPathFinder.defaultArtifactResolveStrategy
import dev.yabd.plugin.internal.config.jira.JiraAttachBuildConfig
import dev.yabd.plugin.internal.core.model.jira.JiraAuthorization
import dev.yabd.plugin.internal.core.model.jira.JiraCloudInstance
import dev.yabd.plugin.internal.core.model.jira.JiraTicket
import dev.yabd.plugin.internal.usecase.jira.JiraAttachBuildLinkInCommentScenario
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class JiraAttachBuildTask : DefaultTask() {
    init {
        group = "Jira"
        description = "Jira file uploader & comment attach"
    }

    @get:Input
    abstract val jiraAttachBuildConfig: Property<JiraAttachBuildConfig>

    @TaskAction
    fun action() {
        with(jiraAttachBuildConfig.get()) {
            val artifactPath = project.defaultArtifactResolveStrategy(filePath, tag)

            logger.apply {
                lifecycle("jira-attach-build  |   email                         : $email")
                lifecycle("jira-attach-build  |   jiraCloudInstance             : $jiraCloudInstance")
                lifecycle("jira-attach-build  |   token                         : $token")
                lifecycle("jira-attach-build  |   ticket                        : $ticket")
                lifecycle("jira-attach-build  |   comment                       : $comment")
                lifecycle("jira-attach-build  |   filePath                      : ${artifactPath.value}")
                artifactName?.let {
                    lifecycle("jira-attach-build  |   artifactName                  : $artifactName")
                }
                val jiraAttachBuildLinkInCommentResponse =
                    JiraAttachBuildLinkInCommentScenario(
                        authorization = JiraAuthorization(email, token),
                        jiraCloudInstance = JiraCloudInstance(jiraCloudInstance),
                        ticket = JiraTicket(ticket),
                        commentPattern = comment,
                        artifactPath = artifactPath,
                        artifactName = artifactName,
                    ).invoke()
                jiraAttachBuildLinkInCommentResponse?.let {
                    lifecycle("jira-attach-build  |   link                          : ${it.self}}")
                }
            }
        }
    }
}
