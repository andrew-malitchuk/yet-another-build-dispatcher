package dev.yabd.plugin.internal.tasks

import dev.yabd.plugin.common.core.file.ArtifactPathFinder.defaultArtifactResolveStrategy
import dev.yabd.plugin.internal.config.JiraAttachBuildConfig
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
    @Suppress("ForbiddenComment")
    fun action() {
        with(jiraAttachBuildConfig.get()) {
            val artifactPath = project.defaultArtifactResolveStrategy(filePath, tag)

            logger.apply {
                lifecycle("jira-attach-build  |   email                          : $email")
                lifecycle("jira-attach-build  |   jiraCloudInstance              : $jiraCloudInstance")
                lifecycle("jira-attach-build  |   token                          : $token")
                lifecycle("jira-attach-build  |   ticket                         : $ticket")
                lifecycle("jira-attach-build  |   comment                        : $comment")
                lifecycle("jira-attach-build  |   filePath                          : ${artifactPath.path}")
                artifactName?.let {
                    lifecycle("jira-config  |   artifactName                : $artifactName")
                }
                val jiraAttachBuildLinkInCommentResponse =
                    JiraAttachBuildLinkInCommentScenario(
                        email = email,
                        token = token,
                        jiraCloudInstance = jiraCloudInstance,
                        ticket = ticket,
                        commentPattern = comment,
                        artifactPath = artifactPath,
                        artifactName = artifactName,
                    ).invoke()
                jiraAttachBuildLinkInCommentResponse?.let {
                    lifecycle("jira-attach-build  |   link                        : $it}")
                }
            }
        }
    }
}
