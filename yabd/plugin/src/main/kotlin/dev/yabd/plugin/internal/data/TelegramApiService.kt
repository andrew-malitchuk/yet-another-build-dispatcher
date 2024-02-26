package dev.yabd.plugin.internal.data

import dev.yabd.plugin.internal.data.TelegramApiService.Url.BASE_URL
import dev.yabd.plugin.internal.data.TelegramApiService.Url.PATH
import dev.yabd.plugin.internal.usecase.telegram.model.TelegramAuthorizationNetModel
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.MultipartFormBody
import org.http4k.core.Response

object TelegramApiService {
    fun HttpHandler.uploadFile(
        chatId: String,
        authorization: TelegramAuthorizationNetModel,
        body: MultipartFormBody,
    ): Response {
        fun getPath(botToken: String): String {
            return "$BASE_URL${PATH.replace("{${Url.Variables.BOT_TOKEN}}", botToken)}"
        }

        val request =
            org.http4k.core.Request(
                method = Method.POST,
                uri = getPath(authorization.token),
            )
                .header("content-type", "multipart/form-data; boundary=${body.boundary}")
                .body(body)
                .query(Url.Variables.CHAT_ID, chatId)

        return invoke(request)
    }

    object Url {
        object Variables {
            const val BOT_TOKEN = "botToken"
            const val CHAT_ID = "chat_id"
            const val DOCUMENT = "document"
        }

        const val BASE_URL = "https://api.telegram.org"
        const val PATH = "/bot{${Variables.BOT_TOKEN}}/sendDocument"
    }
}
