package dev.yabd.plugin.internal.usecase.jira

import dev.yabd.plugin.common.core.ext.containsExtension
import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.usecase.base.UseCase
import dev.yabd.plugin.internal.usecase.jira.model.response.JiraFileUploadResponseNetModel
import dev.yabd.plugin.internal.usecase.jira.model.response.JiraFileUploadResponseNetModel.Companion.toJiraFileUploadResponseNetModel
import org.gradle.api.GradleException
import org.http4k.client.ApacheClient
import org.http4k.core.ContentType
import org.http4k.core.Method
import org.http4k.core.MultipartFormBody
import org.http4k.lens.MultipartFormFile
import java.io.File
import java.util.Base64.getEncoder

/**
 * https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-attachments/#api-group-issue-attachments
 */
class JiraFileUploadUseCase(
    private val email: String?,
    private val token: String?,
    private val jiraCloudInstance: String?,
    private val ticket: String?,
    private val artifactPath: ArtifactPath,
    private val artifactName: String? = null,
) : UseCase() {
    @Suppress("ForbiddenComment")
    override fun invoke(): JiraFileUploadResponseNetModel? {
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

        val client = ApacheClient()

        var file = File(artifactPath.path)

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

        val request =
            org.http4k.core.Request(
                method = Method.POST,
                uri = getPath(jiraCloudInstance, ticket),
            )
                .header("X-Atlassian-Token", "nocheck")
                .header(
                    "Authorization",
                    "Basic " + getEncoder().encodeToString("$email:$token".toByteArray()),
                )
                .header("content-type", "multipart/form-data; boundary=${body.boundary}")
                .body(body)

        val response = client(request)
        return if (response.status.successful) {
            response.toJiraFileUploadResponseNetModel()
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
            const val FILE = "file"
        }

        const val BASE_URL = "https://{${Variables.JIRA_CLOUD_INSTANCE}}"
        const val PATH = "/rest/api/3/issue/{${Variables.TICKET}}/attachments"
    }
}
