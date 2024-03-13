package dev.yabd.plugin.internal.usecase.jira

import dev.yabd.plugin.internal.core.model.jira.JiraAuthorization
import dev.yabd.plugin.internal.core.model.jira.JiraCloudInstance
import dev.yabd.plugin.internal.core.model.jira.JiraTicket
import dev.yabd.plugin.internal.core.utils.JiraUtils.getDownloadLinkComment
import dev.yabd.plugin.internal.data.jira.JiraApiService.leaveComment
import dev.yabd.plugin.internal.data.jira.model.request.JiraCommentRequestNetModel.Companion.toRequestBody
import dev.yabd.plugin.internal.data.jira.model.response.JiraCommentResponseNetModel
import dev.yabd.plugin.internal.data.jira.model.response.JiraCommentResponseNetModel.Companion.toJiraCommentResponseNetModel
import dev.yabd.plugin.internal.usecase.base.UseCase
import org.gradle.api.GradleException
import org.http4k.client.ApacheClient

/**
 * Use case for leaving a comment on a Jira ticket.
 *
 * @param authorization The authorization details for accessing Jira.
 * @param jiraCloudInstance The Jira cloud instance to which the ticket belongs.
 * @param ticket The Jira ticket on which the comment will be left.
 * @param comment The comment to be left on the Jira ticket.
 */
class JiraCommentUseCase(
    private val authorization: JiraAuthorization,
    private val jiraCloudInstance: JiraCloudInstance,
    private val ticket: JiraTicket,
    private val comment: String?,
) : UseCase() {

    /**
     * Executes the use case to leave a comment on the specified Jira ticket.
     *
     * @return The response model containing the comment information if successful, otherwise null.
     */
    override fun invoke(): JiraCommentResponseNetModel? {
        require(!comment.isNullOrBlank()) {
            "`comment` is invalid; please, check it"
        }

        val body = getDownloadLinkComment(comment).toRequestBody()
        require(body != null) {
            "failed to generate `body`"
        }
        val response = ApacheClient().leaveComment(
            jiraCloudInstance,
            ticket,
            authorization,
            body,
        )
        return if (response.status.successful) {
            response.toJiraCommentResponseNetModel()
        } else {
            throw GradleException(
                "JiraUploader |   failed to upload build: " +
                        "${response.status.code}: ${response.status.description} (${response.bodyString()})",
            )
        }
    }
}
