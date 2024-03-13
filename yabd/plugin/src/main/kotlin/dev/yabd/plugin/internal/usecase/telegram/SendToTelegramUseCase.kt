package dev.yabd.plugin.internal.usecase.telegram

import dev.yabd.plugin.common.core.ext.containsExtension
import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.core.model.telegram.TelegramChat
import dev.yabd.plugin.internal.core.model.telegram.TelegramToken
import dev.yabd.plugin.internal.data.telegram.TelegramApiService.Url.Variables.DOCUMENT
import dev.yabd.plugin.internal.data.telegram.TelegramApiService.uploadFile
import dev.yabd.plugin.internal.data.telegram.model.response.TelegramResponseNetModel
import dev.yabd.plugin.internal.data.telegram.model.response.TelegramResponseNetModel.Companion.toTelegramResponseNetModel
import dev.yabd.plugin.internal.usecase.base.UseCase
import org.gradle.api.GradleException
import org.http4k.client.ApacheClient
import org.http4k.core.ContentType
import org.http4k.core.MultipartFormBody
import org.http4k.lens.MultipartFormFile
import java.io.File

/**
 * Use case for sending a file to a Telegram chat.
 *
 * @param chatId The Telegram chat ID to which the file will be sent.
 * @param token The Telegram bot token for authentication.
 * @param artifactPath The path to the file to be sent.
 * @param artifactName The name of the file to be sent.
 *
 * [API documentation](https://core.telegram.org/bots/api#senddocument)
 */
class SendToTelegramUseCase(
    private val chatId: TelegramChat,
    private val token: TelegramToken,
    private val artifactPath: ArtifactPath,
    private val artifactName: String? = null,
) : UseCase() {

    /**
     * Executes the use case to send a file to a Telegram chat.
     *
     * @return The response model containing the sending information if successful, otherwise null.
     */
    override fun invoke(): TelegramResponseNetModel? {
        // Prepare the file to be sent
        var file = File(artifactPath.value)

        // Rename the file if a new name is provided and it has an "apk" extension
        if (!artifactName.isNullOrBlank()) {
            if (artifactName.containsExtension("apk")) {
                val newFile = File(file.parentFile.path + "/$artifactName")
                file.renameTo(newFile)
                file = newFile
            }
        }

        // Create the request body with the file
        val body =
            MultipartFormBody()
                .plus(
                    DOCUMENT to
                            MultipartFormFile(
                                file.name,
                                ContentType.OCTET_STREAM,
                                file.inputStream(),
                            ),
                )

        // Send the file to the Telegram chat
        val response = ApacheClient().uploadFile(chatId, token, body)

        // Check if the file sending was successful and return the response model
        return if (response.status.successful) {
            response.toTelegramResponseNetModel()
        } else {
            throw GradleException(
                "${this::class.java}    |   failed to upload build: " +
                        "${response.status.code}: ${response.status.description} (${response.bodyString()})",
            )
        }
    }
}
