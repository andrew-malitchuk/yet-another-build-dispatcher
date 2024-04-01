package dev.yabd.plugin.internal.data.telegram

import dev.yabd.plugin.internal.core.model.telegram.TelegramChat
import dev.yabd.plugin.internal.core.model.telegram.TelegramToken
import dev.yabd.plugin.internal.data.telegram.TelegramApiService.Url.SEND_MESSAGE
import dev.yabd.plugin.internal.data.telegram.TelegramApiService.Url.UPLOAD_FILE
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.MultipartFormBody
import org.http4k.core.Response

object TelegramApiService {
    fun HttpHandler.uploadFile(
        chatId: TelegramChat,
        authorization: TelegramToken,
        body: MultipartFormBody,
    ): Response {
        fun getPath(botToken: String): String {
            return UPLOAD_FILE.replace("{${Url.Variables.BOT_TOKEN}}", botToken)
        }

        val request =
            org.http4k.core.Request(
                method = Method.POST,
                uri =
                    getPath(
                        authorization.value
                            ?: run { throw IllegalArgumentException("`authorization` is invalid") },
                    ),
            )
                .header("content-type", "multipart/form-data; boundary=${body.boundary}")
                .body(body)
                .query(Url.Variables.CHAT_ID, chatId.value)

        return invoke(request)
    }

    fun HttpHandler.sendMessage(
        chatId: TelegramChat,
        authorization: TelegramToken,
        body: MultipartFormBody,
    ): Response {
        fun getPath(botToken: String): String {
            return SEND_MESSAGE.replace("{${Url.Variables.BOT_TOKEN}}", botToken)
        }

        val request =
            org.http4k.core.Request(
                method = Method.POST,
                uri =
                    getPath(
                        authorization.value
                            ?: run { throw IllegalArgumentException("`authorization` is invalid") },
                    ),
            )
                .header("content-type", "multipart/form-data; boundary=${body.boundary}")
                .body(body)
                .query(Url.Variables.CHAT_ID, chatId.value)

        return invoke(request)
    }

    object Url {
        object Variables {
            const val BOT_TOKEN = "botToken"
            const val CHAT_ID = "chat_id"
            const val DOCUMENT = "document"
        }

        private const val BASE_URL = "https://api.telegram.org"
        const val UPLOAD_FILE = "$BASE_URL/bot{${Variables.BOT_TOKEN}}/sendDocument"
        const val SEND_MESSAGE = "$BASE_URL/bot{${Variables.BOT_TOKEN}}/sendMessage"
    }
}
