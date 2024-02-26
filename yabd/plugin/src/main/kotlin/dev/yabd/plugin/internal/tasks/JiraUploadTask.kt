package dev.yabd.plugin.internal.tasks

import dev.yabd.plugin.common.core.file.ArtifactPathFinder.defaultArtifactResolveStrategy
import dev.yabd.plugin.internal.config.JiraConfig
import dev.yabd.plugin.internal.usecase.jira.JiraFileUploadUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class JiraUploadTask : DefaultTask() {
    init {
        group = "Jira"
        description = "Jira file uploader"
    }

    @get:Input
    abstract val jiraConfig: Property<JiraConfig>

    @TaskAction
    @Suppress("ForbiddenComment")
    fun action() {
        with(jiraConfig.get()) {
            val artifactPath = project.defaultArtifactResolveStrategy(filePath, tag)

            logger.apply {
                lifecycle("jira-config  |   buildVariant                : $tag")
                lifecycle("jira-config  |   email                       : $email")
                lifecycle("jira-config  |   jiraCloudInstance           : $jiraCloudInstance")
                lifecycle("jira-config  |   token                       : $token")
                lifecycle("jira-config  |   ticket                      : $ticket")
                lifecycle("jira-config  |   filePath                    : ${artifactPath.path}")
                artifactName?.let {
                    lifecycle("jira-config  |   artifactName                : $artifactName")
                }
                val response =
                    JiraFileUploadUseCase(
                        email = email,
                        token = token,
                        jiraCloudInstance = jiraCloudInstance,
                        ticket = ticket,
                        artifactPath = artifactPath,
                        artifactName = artifactName,
                    ).invoke()
                response?.let {
                    lifecycle("jira-config  |   link                        : ${it.firstOrNull()?.content}")
                }
            }
        }
    }
}
