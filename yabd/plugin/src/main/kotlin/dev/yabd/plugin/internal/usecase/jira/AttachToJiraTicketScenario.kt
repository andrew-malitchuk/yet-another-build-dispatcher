package dev.yabd.plugin.internal.usecase.jira

import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.core.model.jira.JiraAuthorization
import dev.yabd.plugin.internal.core.model.jira.JiraCloudInstance
import dev.yabd.plugin.internal.core.model.jira.JiraTicket
import dev.yabd.plugin.internal.core.utils.JiraUtils
import dev.yabd.plugin.internal.data.jira.model.response.JiraCommentResponseNetModel
import dev.yabd.plugin.internal.usecase.base.UseCase
import org.gradle.api.GradleException

// TODO: add some wrapper over jira configs
@Suppress("LongParameterList", "ForbiddenComment")
class AttachToJiraTicketScenario(
    private val authorization: JiraAuthorization,
    private val jiraCloudInstance: JiraCloudInstance,
    private val ticket: JiraTicket,
    // TODO: maybe, add some wrapper over it?
    // TODO: add regex
    private val commentPattern: String?,
    private val artifactPath: ArtifactPath,
    private val artifactName: String? = null,
) : UseCase() {
    override fun invoke(): JiraCommentResponseNetModel? {
        require(!commentPattern.isNullOrBlank()) {
            "`comment` is invalid; please, check it"
        }

        val jiraUploadUseCase =
            JiraUploadUseCase(
                authorization = authorization,
                jiraCloudInstance = jiraCloudInstance,
                ticket = ticket,
                artifactPath = artifactPath,
                artifactName = artifactName,
            )
        val jiraFileUploadResponse = jiraUploadUseCase()
        if (jiraFileUploadResponse != null) {
            val jiraCommentUseCase =
                JiraCommentUseCase(
                    authorization = authorization,
                    jiraCloudInstance = jiraCloudInstance,
                    ticket = ticket,
                    comment =
                        JiraUtils.getDownloadLinkComment(
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
