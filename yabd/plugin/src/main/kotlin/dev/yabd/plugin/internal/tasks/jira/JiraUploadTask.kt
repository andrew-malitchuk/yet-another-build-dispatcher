package dev.yabd.plugin.internal.tasks.jira

import dev.yabd.plugin.common.core.file.ArtifactPathFinder.defaultArtifactResolveStrategy
import dev.yabd.plugin.internal.config.jira.JiraConfig
import dev.yabd.plugin.internal.core.model.jira.JiraAuthorization
import dev.yabd.plugin.internal.core.model.jira.JiraCloudInstance
import dev.yabd.plugin.internal.core.model.jira.JiraTicket
import dev.yabd.plugin.internal.core.utils.LoggerUtils.logInfo
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
                    logInfo()
                    lifecycle("jira-upload  |   buildVariant                : $tag")
                    lifecycle("jira-upload  |   email                       : $email")
                    lifecycle("jira-upload  |   jiraCloudInstance           : $jiraCloudInstance")
                    lifecycle("jira-upload  |   token                       : $token")
                    lifecycle("jira-upload  |   ticket                      : $ticket")
                    lifecycle("jira-upload  |   filePath                    : ${artifactPath.value}")
                    artifactName?.let {
                        lifecycle("jira-upload  |   artifactName                : $artifactName")
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
                            lifecycle("jira-upload  |   link                        : ${it.firstOrNull()?.content}")
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
