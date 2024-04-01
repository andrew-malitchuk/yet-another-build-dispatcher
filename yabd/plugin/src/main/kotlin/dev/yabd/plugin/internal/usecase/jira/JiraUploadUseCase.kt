package dev.yabd.plugin.internal.usecase.jira

import dev.yabd.plugin.common.core.ext.containsExtension
import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.core.model.jira.JiraAuthorization
import dev.yabd.plugin.internal.core.model.jira.JiraCloudInstance
import dev.yabd.plugin.internal.core.model.jira.JiraTicket
import dev.yabd.plugin.internal.data.jira.JiraApiService.Url.Variables.FILE
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
 * Use case for uploading a file to a Jira ticket as an attachment.
 *
 * @param authorization The authorization details for accessing Jira.
 * @param jiraCloudInstance The Jira cloud instance to which the ticket belongs.
 * @param ticket The Jira ticket to which the file will be attached.
 * @param artifactPath The path to the artifact file to be uploaded.
 * @param artifactName The custom name for the uploaded artifact.
 */
class JiraUploadUseCase(
    private val authorization: JiraAuthorization,
    private val jiraCloudInstance: JiraCloudInstance,
    private val ticket: JiraTicket,
    private val artifactPath: ArtifactPath,
    private val artifactName: String? = null,
) : UseCase() {
    /**
     * Executes the use case to upload a file to the specified Jira ticket as an attachment.
     *
     * @return The response model containing the upload information if successful, otherwise null.
     */
    override fun invoke(): JiraFileUploadResponseNetModel? {
        var file = File(artifactPath.value)

        // Rename the file if a custom name is provided and the file extension is "apk"
        if (!artifactName.isNullOrBlank()) {
            if (artifactName.containsExtension("apk")) {
                val newFile = File(file.parentFile.path + "/$artifactName")
                file.renameTo(newFile)
                file = newFile
            }
        }

        // Create a multipart form body with the file
        val body =
            MultipartFormBody().plus(
                FILE to
                    MultipartFormFile(
                        file.name,
                        ContentType.OCTET_STREAM,
                        file.inputStream(),
                    ),
            )

        // Upload the file to the Jira ticket
        val response =
            ApacheClient().uploadFile(
                jiraCloudInstance,
                ticket,
                authorization,
                body,
            )

        // Check if the upload was successful and return the response model
        return if (response.status.successful) {
            response.toJiraFileUploadResponseNetModel()
        } else {
            throw GradleException(
                "JiraUploader |   failed to upload build: " +
                    "${response.status.code}: ${response.status.description} (${response.bodyString()})",
            )
        }
    }
}
