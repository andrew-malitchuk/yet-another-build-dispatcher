package dev.yabd.plugin.internal.usecase.slack

import dev.yabd.plugin.common.core.ext.containsExtension
import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.core.model.slack.SlackChannel
import dev.yabd.plugin.internal.core.model.slack.SlackToken
import dev.yabd.plugin.internal.data.slack.SlackApiService.attachFile
import dev.yabd.plugin.internal.data.slack.model.response.SlackResponseNetModel
import dev.yabd.plugin.internal.data.slack.model.response.SlackResponseNetModel.Companion.toSlackResponseNetModel
import dev.yabd.plugin.internal.usecase.base.UseCase
import org.gradle.api.GradleException
import org.http4k.client.ApacheClient
import org.http4k.core.ContentType
import org.http4k.core.MultipartFormBody
import org.http4k.lens.MultipartFormFile
import java.io.File

/**
 * Use case for sharing a file on Slack.
 *
 * @param token The Slack API token for authentication.
 * @param channel The Slack channel on which the file will be shared.
 * @param artifactPath The path to the artifact file to be shared.
 * @param artifactName The custom name for the shared artifact.
 */
class ShareOnSlackUseCase(
    private val token: SlackToken,
    private val channel: SlackChannel,
    private val artifactPath: ArtifactPath,
    private val artifactName: String? = null,
) : UseCase() {
    /**
     * Executes the use case to share a file on Slack.
     *
     * @return The response model containing the sharing information if successful, otherwise null.
     */
    override fun invoke(): SlackResponseNetModel? {
        var file = File(artifactPath.value)

        // Rename the file if a custom name is provided and the file extension is "apk"
        if (!artifactName.isNullOrBlank()) {
            if (artifactName.containsExtension("apk")) {
                val newFile = File(file.parentFile.path + "/$artifactName")
                file.renameTo(newFile)
                file = newFile
            }
        }

        // Create a multipart form body with the file, initial comment, and channel
        val body =
            MultipartFormBody().plus(
                "file" to
                    MultipartFormFile(
                        file.name,
                        ContentType.OCTET_STREAM,
                        file.inputStream(),
                    ),
            ).plus(
                "channels" to "${channel.value}",
            )

        // Share the file on Slack
        val response = ApacheClient().attachFile(token, body)

        // Check if the sharing was successful and return the response model
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
