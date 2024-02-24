package dev.yabd.plugin.internal.tasks

import dev.yabd.plugin.common.core.file.ArtifactPathFinder.defaultArtifactResolveStrategy
import dev.yabd.plugin.internal.config.JiraCommentConfig
import dev.yabd.plugin.internal.core.utils.JiraUtils.getDownloadLinkComment
import dev.yabd.plugin.internal.usecase.jira.JiraCommentUseCase
import dev.yabd.plugin.internal.usecase.jira.JiraFileUploadUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
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
            val artifactPath = project.defaultArtifactResolveStrategy(filePath, tag)

            logger.apply {
                lifecycle("jira-comment  |   buildVariant                : $tag")
                lifecycle("jira-comment  |   email                       : $email")
                lifecycle("jira-comment  |   jiraCloudInstance           : $jiraCloudInstance")
                lifecycle("jira-comment  |   token                       : $token")
                lifecycle("jira-comment  |   ticket                      : $ticket")
                lifecycle("jira-comment  |   filePath                    : ${artifactPath.path}")
                artifactName?.let {
                    lifecycle("jira-comment  |   artifactName                : $artifactName")
                }
                val jiraFileUploadResponse =
                    JiraFileUploadUseCase(
                        email = email,
                        token = token,
                        jiraCloudInstance = jiraCloudInstance,
                        ticket = ticket,
                        artifactPath = artifactPath,
                        artifactName = artifactName,
                    ).invoke() ?: throw GradleException("SWW during file uploading")

                lifecycle("jira-comment  |   link                        : $jiraFileUploadResponse}")
                val jiraCommentResponse =
                    JiraCommentUseCase(
                        email = email,
                        token = token,
                        jiraCloudInstance = jiraCloudInstance,
                        ticket = ticket,
                        comment = getDownloadLinkComment(jiraFileUploadResponse),
                    ).invoke()
                jiraCommentResponse?.let {
                    lifecycle("jira-comment  |   link                        : $it}")
                }
            }
        }
    }
}
