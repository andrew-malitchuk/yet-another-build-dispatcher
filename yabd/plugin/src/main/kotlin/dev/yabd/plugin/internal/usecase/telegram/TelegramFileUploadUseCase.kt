package dev.yabd.plugin.internal.usecase.telegram

import dev.yabd.plugin.common.core.ext.containsExtension
import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.data.TelegramApiService.Url.Variables.DOCUMENT
import dev.yabd.plugin.internal.data.TelegramApiService.uploadFile
import dev.yabd.plugin.internal.usecase.base.UseCase
import dev.yabd.plugin.internal.usecase.telegram.model.TelegramAuthorizationNetModel
import dev.yabd.plugin.internal.usecase.telegram.model.TelegramResponseNetModel
import dev.yabd.plugin.internal.usecase.telegram.model.TelegramResponseNetModel.Companion.toTelegramResponseNetModel
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
class TelegramFileUploadUseCase(
    private val chatId: String?,
    private val token: String?,
    private val artifactPath: ArtifactPath,
    private val artifactName: String? = null,
) : UseCase() {
    override operator fun invoke(): TelegramResponseNetModel? {
        require(!chatId.isNullOrBlank()) {
            "`chatId` is invalid; please, check it"
        }
        require(!token.isNullOrBlank()) {
            "`token` is invalid; please, check it"
        }

        var file = File(artifactPath.path)

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

        val response = ApacheClient().uploadFile(chatId, TelegramAuthorizationNetModel(token), body)

        return if (response.status.successful) {
            response.toTelegramResponseNetModel()
        } else {
            throw GradleException(
                "TelegramUploader |   failed to upload build: " +
                    "${response.status.code}: ${response.status.description} (${response.bodyString()})",
            )
        }
    }
}
