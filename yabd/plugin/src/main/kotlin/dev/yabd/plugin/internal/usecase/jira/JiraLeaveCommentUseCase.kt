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
 * https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-comments/#api-rest-api-3-issue-issueidorkey-comment-post
 */
class JiraLeaveCommentUseCase(
    private val authorization: JiraAuthorization,
    private val jiraCloudInstance: JiraCloudInstance,
    private val ticket: JiraTicket,
    private val comment: String?,
) : UseCase() {
    override fun invoke(): JiraCommentResponseNetModel? {
        require(!comment.isNullOrBlank()) {
            "`comment` is invalid; please, check it"
        }

        val body = getDownloadLinkComment(comment).toRequestBody()
        require(body != null) {
            "failed to generate `body`"
        }
        val response =
            ApacheClient().leaveComment(
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
