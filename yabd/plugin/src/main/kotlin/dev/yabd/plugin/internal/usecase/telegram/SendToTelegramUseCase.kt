package dev.yabd.plugin.internal.usecase.telegram

import dev.yabd.plugin.common.core.ext.containsExtension
import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.core.model.telegram.TelegramChatId
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
 *
 * @param chatId
 * @param token
 * @param artifactPath
 *
 * [API documentation] (https://core.telegram.org/bots/api#senddocument)
 */
class SendToTelegramUseCase(
    private val chatId: TelegramChatId,
    private val token: TelegramToken,
    private val artifactPath: ArtifactPath,
    private val artifactName: String? = null,
) : UseCase() {
    override operator fun invoke(): TelegramResponseNetModel? {
        var file = File(artifactPath.value)

        if (!artifactName.isNullOrBlank()) {
            if (artifactName.containsExtension("apk")) {
                val newFile = File(file.parentFile.path + "/$artifactName")
                file.renameTo(newFile)
                file = newFile
            }
        }

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

        val response = ApacheClient().uploadFile(chatId, token, body)

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
