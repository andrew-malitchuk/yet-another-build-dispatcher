package dev.yabd.plugin.internal.tasks.slack

import dev.yabd.plugin.common.core.file.ArtifactPathFinder.defaultArtifactResolveStrategy
import dev.yabd.plugin.internal.config.slack.SlackConfig
import dev.yabd.plugin.internal.core.model.slack.SlackChannel
import dev.yabd.plugin.internal.core.model.slack.SlackToken
import dev.yabd.plugin.internal.usecase.slack.SlackFileUploadUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class SlackUploadTask : DefaultTask() {
    init {
        group = "Slack"
        description = "Slack file uploader"
    }

    @get:Input
    abstract val slackConfig: Property<SlackConfig>

    @Option(description = DEBUG_DESCRIPTION, option = DEBUG_OUTPUT)
    @get:Input
    var debugOutput: Boolean = false

    @Suppress("NestedBlockDepth")
    @TaskAction
    fun action() {
        with(slackConfig.get()) {
            val artifactPath = project.defaultArtifactResolveStrategy(filePath, tag)

            if (debugOutput) {
                logger.apply {
                    lifecycle("slack-config |  buildVariant     : $tag")
                    lifecycle("slack-config |  channel          : $channel")
                    lifecycle("slack-config |  token            : $token")
                    lifecycle("slack-config |  filePath         : ${artifactPath.value}")
                    artifactName?.let {
                        lifecycle("slack-config |   artifactName    : $artifactName")
                    }
                }
            }
            val response =
                SlackFileUploadUseCase(
                    SlackToken(token),
                    SlackChannel(channel),
                    artifactPath,
                    artifactName,
                ).invoke()

            if (debugOutput) {
                logger.apply {
                    response?.file?.let {
                        lifecycle("slack-config |   link            : ${it.permalink}")
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
