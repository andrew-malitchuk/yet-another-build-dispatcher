package dev.yabd.plugin.internal.tasks.jira

import dev.yabd.plugin.common.core.file.ArtifactPathFinder.defaultArtifactResolveStrategy
import dev.yabd.plugin.internal.config.jira.JiraConfig
import dev.yabd.plugin.internal.core.model.jira.JiraAuthorization
import dev.yabd.plugin.internal.core.model.jira.JiraCloudInstance
import dev.yabd.plugin.internal.core.model.jira.JiraTicket
import dev.yabd.plugin.internal.usecase.jira.JiraUploadUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class JiraUploadTask : DefaultTask() {
    init {
        group = "Jira"
        description = "Jira file uploader"
    }

    @get:Input
    abstract val jiraConfig: Property<JiraConfig>

    @Option(description = DEBUG_DESCRIPTION, option = DEBUG_OUTPUT)
    @get:Input
    var debugOutput: Boolean = false

    @Suppress("NestedBlockDepth")
    @TaskAction
    fun action() {
        with(jiraConfig.get()) {
            val artifactPath = project.defaultArtifactResolveStrategy(filePath, tag)
            if (debugOutput) {
                logger.apply {
                    lifecycle("jira-config  |   buildVariant                : $tag")
                    lifecycle("jira-config  |   email                       : $email")
                    lifecycle("jira-config  |   jiraCloudInstance           : $jiraCloudInstance")
                    lifecycle("jira-config  |   token                       : $token")
                    lifecycle("jira-config  |   ticket                      : $ticket")
                    lifecycle("jira-config  |   filePath                    : ${artifactPath.value}")
                    artifactName?.let {
                        lifecycle("jira-config  |   artifactName                : $artifactName")
                    }
                }
                val response =
                    JiraUploadUseCase(
                        authorization = JiraAuthorization(email, token),
                        jiraCloudInstance = JiraCloudInstance(jiraCloudInstance),
                        ticket = JiraTicket(ticket),
                        artifactPath = artifactPath,
                        artifactName = artifactName,
                    ).invoke()
                if (debugOutput) {
                    logger.apply {
                        response?.let {
                            lifecycle("jira-config  |   link                        : ${it.firstOrNull()?.content}")
                        }
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
