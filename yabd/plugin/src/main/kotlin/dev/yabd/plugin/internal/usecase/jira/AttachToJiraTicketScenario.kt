package dev.yabd.plugin.internal.usecase.jira

import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.core.model.jira.JiraAuthorization
import dev.yabd.plugin.internal.core.model.jira.JiraCloudInstance
import dev.yabd.plugin.internal.core.model.jira.JiraTicket
import dev.yabd.plugin.internal.core.utils.JiraUtils
import dev.yabd.plugin.internal.data.jira.model.response.JiraCommentResponseNetModel
import dev.yabd.plugin.internal.usecase.base.UseCase
import org.gradle.api.GradleException

/**
 * Use case for attaching artifacts to a Jira ticket and posting a comment with the attachment link.
 *
 * @param authorization The authorization details for accessing Jira.
 * @param jiraCloudInstance The Jira cloud instance to which the ticket belongs.
 * @param ticket The Jira ticket to which the artifact will be attached.
 * @param commentPattern The pattern for the comment to be posted along with the attachment link.
 * @param artifactPath The path to the artifact to be attached.
 * @param artifactName The custom name for the uploaded artifact.
 */
@Suppress("LongParameterList", "ForbiddenComment")
class AttachToJiraTicketScenario(
    private val authorization: JiraAuthorization,
    private val jiraCloudInstance: JiraCloudInstance,
    private val ticket: JiraTicket,
    private val commentPattern: String?,
    private val artifactPath: ArtifactPath,
    private val artifactName: String? = null,
) : UseCase() {

    /**
     * Executes the use case to attach the artifact to the Jira ticket and post a comment
     * with the attachment link.
     *
     * @return The response model containing the comment information if successful, otherwise null.
     */
    override fun invoke(): JiraCommentResponseNetModel? {
        require(!commentPattern.isNullOrBlank()) {
            "`comment` is invalid; please, check it"
        }

        val jiraUploadUseCase = JiraUploadUseCase(
            authorization = authorization,
            jiraCloudInstance = jiraCloudInstance,
            ticket = ticket,
            artifactPath = artifactPath,
            artifactName = artifactName,
        )
        val jiraFileUploadResponse = jiraUploadUseCase()
        if (jiraFileUploadResponse != null) {
            val jiraCommentUseCase = JiraCommentUseCase(
                authorization = authorization,
                jiraCloudInstance = jiraCloudInstance,
                ticket = ticket,
                comment = JiraUtils.getDownloadLinkComment(
                    pattern = commentPattern,
                    jiraFileUpload = jiraFileUploadResponse,
                ),
            )
            return jiraCommentUseCase()
        } else {
            throw GradleException("Failed to upload a build")
        }
    }
}
