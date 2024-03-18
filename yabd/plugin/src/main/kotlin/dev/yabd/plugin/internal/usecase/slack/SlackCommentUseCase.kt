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

/**
 * Use case for posting a comment on Slack.
 *
 * @param token The Slack API token for authentication.
 * @param channel The Slack channel on which the comment will be posted.
 * @param comment The message content of the comment to be posted.
 */
class SlackCommentUseCase(
    private val token: SlackToken,
    private val channel: SlackChannel,
    private val comment: SlackMessage,
) : UseCase() {

    /**
     * Executes the use case to post a comment on Slack.
     *
     * @return The response model containing the posting information if successful, otherwise null.
     */
    override fun invoke(): SlackResponseNetModel? {
        // Format the comment and convert it to a request body
        val body = SlackUtils.formatComment(channel, comment).toRequestBody()
        require(body != null)

        // Send the message to Slack
        val response = ApacheClient().sendMessage(token, body)

        // Check if the message posting was successful and return the response model
        return if (response.status.successful) {
            response.toSlackResponseNetModel()
        } else {
            throw GradleException(
                "${this::class.java}    |   failed to upload build: " +
                        "${response.status.code}: ${response.status.description} (${response.bodyString()})",
            )
        }
    }
}
