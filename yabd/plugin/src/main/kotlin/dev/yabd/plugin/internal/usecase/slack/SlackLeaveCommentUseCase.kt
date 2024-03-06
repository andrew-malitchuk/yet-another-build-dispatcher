package dev.yabd.plugin.internal.usecase.slack

import dev.yabd.plugin.internal.core.model.slack.SlackChannel
import dev.yabd.plugin.internal.core.model.slack.SlackMessage
import dev.yabd.plugin.internal.core.model.slack.SlackToken
import dev.yabd.plugin.internal.core.utils.SlackUtils
import dev.yabd.plugin.internal.data.slack.SlackApiService.sendMessage
import dev.yabd.plugin.internal.data.slack.model.request.SlackCommentRequestNetModel.Companion.toRequestBody
import dev.yabd.plugin.internal.data.slack.model.response.SlackResponseNetModel
import dev.yabd.plugin.internal.data.slack.model.response.SlackResponseNetModel.Companion.toSlackResponseNetModel
import dev.yabd.plugin.internal.usecase.base.UseCase
import org.gradle.api.GradleException
import org.http4k.client.ApacheClient

class SlackLeaveCommentUseCase(
    private val token: SlackToken,
    private val channel: SlackChannel,
    private val comment: SlackMessage,
) : UseCase() {
    override fun invoke(): SlackResponseNetModel? {
        val body = SlackUtils.formatComment(channel, comment).toRequestBody()
        require(body != null)

        val response = ApacheClient().sendMessage(token, body)

        return if (response.status.successful) {
            response.toSlackResponseNetModel()
        } else {
            throw GradleException(
                "SlackLeaveCommentUseCase |   failed to upload build: " +
                    "${response.status.code}: ${response.status.description} (${response.bodyString()})",
            )
        }
    }
}
