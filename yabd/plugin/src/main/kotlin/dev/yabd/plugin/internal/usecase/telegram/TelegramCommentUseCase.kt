package dev.yabd.plugin.internal.usecase.telegram

import dev.yabd.plugin.internal.core.model.telegram.TelegramChat
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
 * Use case for sending a message to a Telegram chat.
 *
 * @param chatId The Telegram chat ID to which the message will be sent.
 * @param token The Telegram bot token for authentication.
 * @param message The message to be sent.
 *
 * [API documentation](https://core.telegram.org/bots/api#sendmessage)
 */
class TelegramCommentUseCase(
    private val chatId: TelegramChat,
    private val token: TelegramToken,
    private val message: TelegramMessage,
) : UseCase() {
    /**
     * Executes the use case to send a message to a Telegram chat.
     *
     * @return The response model containing the sending information if successful, otherwise null.
     */
    override operator fun invoke(): TelegramResponseNetModel? {
        // Create the request body with the message
        val body = MultipartFormBody().plus("text" to "${message.value}")

        // Send the message to the Telegram chat
        val response = ApacheClient().sendMessage(chatId, token, body)

        // Check if the message sending was successful and return the response model
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
