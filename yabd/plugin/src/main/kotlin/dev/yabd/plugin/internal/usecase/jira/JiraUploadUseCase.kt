package dev.yabd.plugin.internal.usecase.jira

import dev.yabd.plugin.common.core.ext.containsExtension
import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.core.model.jira.JiraAuthorization
import dev.yabd.plugin.internal.core.model.jira.JiraCloudInstance
import dev.yabd.plugin.internal.core.model.jira.JiraTicket
import dev.yabd.plugin.internal.data.jira.JiraApiService.uploadFile
import dev.yabd.plugin.internal.data.jira.model.response.JiraFileUploadResponseNetModel
import dev.yabd.plugin.internal.data.jira.model.response.JiraFileUploadResponseNetModel.Companion.toJiraFileUploadResponseNetModel
import dev.yabd.plugin.internal.usecase.base.UseCase
import org.gradle.api.GradleException
import org.http4k.client.ApacheClient
import org.http4k.core.ContentType
import org.http4k.core.MultipartFormBody
import org.http4k.lens.MultipartFormFile
import java.io.File

/**
 * https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-attachments/#api-group-issue-attachments
 */
class JiraUploadUseCase(
    private val authorization: JiraAuthorization,
    private val jiraCloudInstance: JiraCloudInstance,
    private val ticket: JiraTicket,
    private val artifactPath: ArtifactPath,
    private val artifactName: String? = null,
) : UseCase() {
    @Suppress("ForbiddenComment")
    override fun invoke(): JiraFileUploadResponseNetModel? {
        var file = File(artifactPath.value)

        // TODO: recode
        if (!artifactName.isNullOrBlank()) {
            if (artifactName.containsExtension("apk")) {
                val newFile = File(file.parentFile.path + "/$artifactName")
                file.renameTo(newFile)
                file = newFile
            }
        }

        // TODO: recode
        val body =
            MultipartFormBody()
                .plus(
                    Variables.FILE to
                        MultipartFormFile(
                            file.name,
                            ContentType.OCTET_STREAM,
                            file.inputStream(),
                        ),
                )

        val response =
            ApacheClient().uploadFile(
                jiraCloudInstance,
                ticket,
                authorization,
                body,
            )
        return if (response.status.successful) {
            response.toJiraFileUploadResponseNetModel()
        } else {
            throw GradleException(
                "JiraUploader |   failed to upload build: " +
                    "${response.status.code}: ${response.status.description} (${response.bodyString()})",
            )
        }
    }

    companion object {
        object Variables {
            const val JIRA_CLOUD_INSTANCE = "jiraCloudInstance"
            const val TICKET = "ticket"
            const val FILE = "file"
        }

        const val BASE_URL = "https://{${Variables.JIRA_CLOUD_INSTANCE}}"
        const val PATH = "/rest/api/3/issue/{${Variables.TICKET}}/attachments"
    }
}
