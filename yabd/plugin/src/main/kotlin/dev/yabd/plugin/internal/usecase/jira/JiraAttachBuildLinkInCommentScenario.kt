package dev.yabd.plugin.internal.usecase.jira

import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.core.utils.JiraUtils
import dev.yabd.plugin.internal.usecase.base.UseCase
import dev.yabd.plugin.internal.usecase.jira.model.response.JiraCommentResponseNetModel
import org.gradle.api.GradleException

// TODO: add some wrapper over jira configs
@Suppress("LongParameterList", "ForbiddenComment")
class JiraAttachBuildLinkInCommentScenario(
    private val email: String?,
    private val token: String?,
    // TODO: maybe, add some wrapper over it?
    private val jiraCloudInstance: String?,
    // TODO: maybe, add some wrapper over it?
    private val ticket: String?,
    // TODO: maybe, add some wrapper over it?
    // TODO: add regex
    private val commentPattern: String?,
    private val artifactPath: ArtifactPath,
    private val artifactName: String? = null,
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
        require(!commentPattern.isNullOrBlank()) {
            "`comment` is invalid; please, check it"
        }

        val jiraFileUploadUseCase =
            JiraFileUploadUseCase(
                email = email,
                token = token,
                jiraCloudInstance = jiraCloudInstance,
                ticket = ticket,
                artifactPath = artifactPath,
                artifactName = artifactName,
            )

        val jiraFileUploadResponse = jiraFileUploadUseCase()

        if (jiraFileUploadResponse != null) {
            val jiraLeaveCommentUseCase =
                JiraLeaveCommentUseCase(
                    email = email,
                    token = token,
                    jiraCloudInstance = jiraCloudInstance,
                    ticket = ticket,
                    comment =
                        JiraUtils.getDownloadLinkComment(
                            pattern = commentPattern,
                            jiraFileUpload = jiraFileUploadResponse,
                        ),
                )

            return jiraLeaveCommentUseCase()
        } else {
            throw GradleException("Failed to upload a build")
        }
    }
}
