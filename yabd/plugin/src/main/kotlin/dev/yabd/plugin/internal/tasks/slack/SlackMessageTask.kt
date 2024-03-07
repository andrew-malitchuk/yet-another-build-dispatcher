package dev.yabd.plugin.internal.tasks.slack

import dev.yabd.plugin.internal.config.slack.SlackConfig
import dev.yabd.plugin.internal.core.model.slack.SlackChannel
import dev.yabd.plugin.internal.core.model.slack.SlackMessage
import dev.yabd.plugin.internal.core.model.slack.SlackToken
import dev.yabd.plugin.internal.usecase.slack.SlackLeaveCommentUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class SlackMessageTask : DefaultTask() {
    init {
        group = "Slack"
        description = "Slack message comment"
    }

    @get:Input
    abstract val slackConfig: Property<SlackConfig>

    @Option(description = DEBUG_DESCRIPTION, option = DEBUG_OUTPUT)
    @get:Input
    var debugOutput: Boolean = false

    @Option(description = MESSAGE, option = MESSAGE)
    @get:Input
    var message: String = ""

    @Suppress("NestedBlockDepth")
    @TaskAction
    fun action() {
        with(slackConfig.get()) {
            if (debugOutput) {
                logger.apply {
                    lifecycle("slack-config |  buildVariant     : $tag")
                    lifecycle("slack-config |  buildVariant     : $message")
                    lifecycle("slack-config |  channel          : $channel")
                    lifecycle("slack-config |  token            : $token")
                    artifactName?.let {
                        lifecycle("slack-config |   artifactName    : $artifactName")
                    }
                }
            }
            SlackLeaveCommentUseCase(
                SlackToken(token),
                SlackChannel(channel),
                comment = SlackMessage("foobar"),
            ).invoke()
        }
    }

    companion object {
        const val DEBUG_OUTPUT = "debugOutput"
        const val DEBUG_DESCRIPTION = "debugOutput"
        const val MESSAGE = "message"
    }
}
