package dev.yabd.plugin.internal.usecase.telegram

import dev.yabd.plugin.common.core.ext.containsExtension
import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.usecase.base.UseCase
import dev.yabd.plugin.internal.usecase.telegram.model.TelegramResponseNetModel
import dev.yabd.plugin.internal.usecase.telegram.model.TelegramResponseNetModel.Companion.toTelegramResponseNetModel
import org.gradle.api.GradleException
import org.http4k.client.ApacheClient
import org.http4k.core.ContentType
import org.http4k.core.Method
import org.http4k.core.MultipartFormBody
import org.http4k.core.Request
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

        val client = ApacheClient()

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
                    Variables.DOCUMENT to
                        MultipartFormFile(
                            file.name,
                            ContentType.OCTET_STREAM,
                            file.inputStream(),
                        ),
                )

        val request =
            Request(
                method = Method.POST,
                uri = getPath(token),
            )
                .header("content-type", "multipart/form-data; boundary=${body.boundary}")
                .body(body)
                .query(Variables.CHAT_ID, chatId)

        val response = client(request)
        return if (response.status.successful) {
            response.toTelegramResponseNetModel()
        } else {
            throw GradleException(
                "TelegramUploader |   failed to upload build: " +
                    "${response.status.code}: ${response.status.description} (${response.bodyString()})",
            )
        }
    }

    private fun getPath(botToken: String): String {
        return "$BASE_URL${PATH.replace("{${Variables.BOT_TOKEN}}", botToken)}"
    }

    companion object {
        object Variables {
            const val BOT_TOKEN = "botToken"
            const val CHAT_ID = "chat_id"
            const val DOCUMENT = "document"
        }

        const val BASE_URL = "https://api.telegram.org"
        const val PATH = "/bot{${Variables.BOT_TOKEN}}/sendDocument"
    }
}
