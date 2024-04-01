package dev.yabd.plugin.internal.tasks.slack

import dev.yabd.plugin.internal.config.slack.SlackConfig
import dev.yabd.plugin.internal.core.model.slack.SlackChannel
import dev.yabd.plugin.internal.core.model.slack.SlackMessage
import dev.yabd.plugin.internal.core.model.slack.SlackToken
import dev.yabd.plugin.internal.core.utils.LoggerUtils.logInfo
import dev.yabd.plugin.internal.usecase.slack.SlackCommentUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

/**
 * Task for commenting on Slack messages.
 */
abstract class SlackCommentTask : DefaultTask() {
    init {
        group = "Slack"
        description = "Slack message comment"
    }

    /**
     * Configuration for commenting on Slack messages.
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
     * Option to specify the message content.
     */
    @Option(description = MESSAGE, option = MESSAGE)
    @get:Input
    var message: String = ""

    /**
     * Action method for the task.
     */
    @Suppress("NestedBlockDepth")
    @TaskAction
    fun action() {
        with(slackConfig.get()) {
            if (debugOutput) {
                logger.apply {
                    logInfo()
                    lifecycle("slack-comment    |  buildVariant     : $tag")
                    lifecycle("slack-comment    |  message          : $message")
                    lifecycle("slack-comment    |  channel          : $channel")
                    lifecycle("slack-comment    |  token            : $token")
                    artifactName?.let {
                        lifecycle("slack-comment    |   artifactName    : $artifactName")
                    }
                }
            }
            SlackCommentUseCase(
                SlackToken(token),
                SlackChannel(channel),
                comment = SlackMessage(message),
            ).invoke()
        }
    }

    companion object {
        const val DEBUG_OUTPUT = "debugOutput"
        const val DEBUG_DESCRIPTION = "debugOutput"
        const val MESSAGE = "message"
    }
}
