package dev.yabd.plugin.internal.usecase.telegram

import dev.yabd.plugin.internal.core.model.telegram.TelegramChatId
import dev.yabd.plugin.internal.core.model.telegram.TelegramMessage
import dev.yabd.plugin.internal.core.model.telegram.TelegramToken
import dev.yabd.plugin.internal.data.telegram.TelegramApiService.sendMessage
import dev.yabd.plugin.internal.data.telegram.model.response.TelegramResponseNetModel
import dev.yabd.plugin.internal.data.telegram.model.response.TelegramResponseNetModel.Companion.toTelegramResponseNetModel
import dev.yabd.plugin.internal.usecase.base.UseCase
import org.gradle.api.GradleException
import org.http4k.client.ApacheClient
import org.http4k.core.MultipartFormBody

/**
 *
 * @param chatId
 * @param token
 * @param artifactPath
 *
 * [API documentation] (https://core.telegram.org/bots/api#senddocument)
 */
class TelegramCommentUseCase(
    private val chatId: TelegramChatId,
    private val token: TelegramToken,
    private val message: TelegramMessage,
) : UseCase() {
    override operator fun invoke(): TelegramResponseNetModel? {
        val body = MultipartFormBody().plus("text" to "${message.value}")
        val response = ApacheClient().sendMessage(chatId, token, body)
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
