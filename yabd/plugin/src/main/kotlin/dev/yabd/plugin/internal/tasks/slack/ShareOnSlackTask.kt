package dev.yabd.plugin.internal.tasks.slack

import dev.yabd.plugin.common.core.file.ArtifactPathFinder.defaultArtifactResolveStrategy
import dev.yabd.plugin.internal.config.slack.SlackConfig
import dev.yabd.plugin.internal.core.model.slack.SlackChannel
import dev.yabd.plugin.internal.core.model.slack.SlackToken
import dev.yabd.plugin.internal.core.utils.LoggerUtils.logInfo
import dev.yabd.plugin.internal.usecase.slack.ShareOnSlackUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

/**
 * Task for sharing files on Slack.
 */
abstract class ShareOnSlackTask : DefaultTask() {
    init {
        group = "Slack"
        description = "Slack file uploader"
    }

    /**
     * Configuration for sharing files on Slack.
     */
    @get:Input
    abstract val slackConfig: Property<SlackConfig>

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
        with(slackConfig.get()) {
            val artifactPath = project.defaultArtifactResolveStrategy(filePath, tag)

            if (debugOutput) {
                logger.apply {
                    logInfo()
                    lifecycle("share-on-slack   |  buildVariant     : $tag")
                    lifecycle("share-on-slack   |  channel          : $channel")
                    lifecycle("share-on-slack   |  token            : $token")
                    lifecycle("share-on-slack   |  filePath         : ${artifactPath.value}")
                    artifactName?.let {
                        lifecycle("share-on-slack   |   artifactName    : $artifactName")
                    }
                }
            }
            val response =
                ShareOnSlackUseCase(
                    SlackToken(token),
                    SlackChannel(channel),
                    artifactPath,
                    artifactName,
                ).invoke()

            if (debugOutput) {
                logger.apply {
                    response?.file?.let {
                        lifecycle("share-on-slack   |   link            : ${it.permalink}")
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
