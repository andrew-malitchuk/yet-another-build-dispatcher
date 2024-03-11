package dev.yabd.plugin.internal.core.utils

import dev.yabd.plugin.internal.core.model.slack.SlackChannel
import dev.yabd.plugin.internal.core.model.slack.SlackMessage
import dev.yabd.plugin.internal.data.slack.model.request.BlockCommentRequestNetModel
import dev.yabd.plugin.internal.data.slack.model.request.SlackCommentRequestNetModel
import dev.yabd.plugin.internal.data.slack.model.request.TextRequestNetModel

object SlackUtils {
    fun formatComment(
        channel: SlackChannel,
        comment: SlackMessage,
    ): SlackCommentRequestNetModel {
        return SlackCommentRequestNetModel(
            blocks =
                listOf(
                    BlockCommentRequestNetModel(
                        text =
                            TextRequestNetModel(
                                text = comment.value,
                                type = "mrkdwn",
                            ),
                        type = "section",
                    ),
                ),
            channel = channel.value,
        )
    }
}
