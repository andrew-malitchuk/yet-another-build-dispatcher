package dev.yabd.plugin.internal.usecase.jira

import dev.yabd.plugin.internal.core.utils.JiraUtils.getDownloadLinkComment
import dev.yabd.plugin.internal.usecase.base.UseCase
import dev.yabd.plugin.internal.usecase.jira.model.request.JiraCommentRequestNetModel.Companion.toRequestBody
import dev.yabd.plugin.internal.usecase.jira.model.response.JiraCommentResponseNetModel
import dev.yabd.plugin.internal.usecase.jira.model.response.JiraCommentResponseNetModel.Companion.toJiraCommentResponseNetModel
import org.gradle.api.GradleException
import org.http4k.client.ApacheClient
import org.http4k.core.Method
import java.util.Base64

/**
 * https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-comments/#api-rest-api-3-issue-issueidorkey-comment-post
 */
@Suppress("ForbiddenComment")
class JiraLeaveCommentUseCase(
    private val email: String?,
    private val token: String?,
    private val jiraCloudInstance: String?,
    private val ticket: String?,
    private val comment: String?,
) : UseCase() {
    override fun invoke(): JiraCommentResponseNetModel? {
        require(!email.isNullOrBlank()) {
            "`email` is invalid; please, check it"
        }
        require(!token.isNullOrBlank()) {
            "`token` is invalid; please, check it"
        }
        require(!jiraCloudInstance.isNullOrBlank()) {
            "`jiraCloudInstance` is invalid; please, check it"
        }
        require(!ticket.isNullOrBlank()) {
            "`ticket` is invalid; please, check it"
        }
        require(!comment.isNullOrBlank()) {
            "`comment` is invalid; please, check it"
        }

        val client = ApacheClient()

        val body = getDownloadLinkComment(comment).toRequestBody()
        require(body != null) {
            "failed to generate `body`"
        }
        // TODO: recode to factory method or abstract factory
        val request =
            org.http4k.core.Request(
                method = Method.POST,
                uri = getPath(jiraCloudInstance, ticket),
            )
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header(
                    "Authorization",
                    "Basic " + Base64.getEncoder().encodeToString("$email:$token".toByteArray()),
                )
                .body(body)
        val response = client(request)
        return if (response.status.successful) {
            response.toJiraCommentResponseNetModel()
        } else {
            throw GradleException(
                "JiraUploader |   failed to upload build: " +
                    "${response.status.code}: ${response.status.description} (${response.bodyString()})",
            )
        }
    }

    private fun getPath(
        jiraCloudInstance: String,
        ticket: String,
    ): String {
        return "${
            BASE_URL.replace(
                "{${Variables.JIRA_CLOUD_INSTANCE}}",
                jiraCloudInstance,
            )
        }${PATH.replace("{${Variables.TICKET}}", ticket)}"
    }

    companion object {
        object Variables {
            const val JIRA_CLOUD_INSTANCE = "jiraCloudInstance"
            const val TICKET = "ticket"
        }

        const val BASE_URL = "https://{${Variables.JIRA_CLOUD_INSTANCE}}"
        const val PATH = "/rest/api/3/issue/{${Variables.TICKET}}/comment"
    }
}
