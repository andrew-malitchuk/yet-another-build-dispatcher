package dev.yabd.plugin.internal.data.slack

import dev.yabd.plugin.internal.core.model.slack.SlackToken
import dev.yabd.plugin.internal.data.slack.SlackApiService.Url.FILE_UPLOAD
import dev.yabd.plugin.internal.data.slack.SlackApiService.Url.POST_MESSAGE
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.MultipartFormBody
import org.http4k.core.Request
import org.http4k.core.Response

object SlackApiService {
    fun HttpHandler.attachFile(
        token: SlackToken,
        body: MultipartFormBody,
    ): Response {
        val request =
            Request(Method.POST, FILE_UPLOAD)
                .header("Authorization", "Bearer ${token.value}")
                .header("content-type", "multipart/form-data; boundary=${body.boundary}")
                .body(
                    body,
                )
        return invoke(request)
    }

    fun HttpHandler.sendMessage(
        token: SlackToken,
        body: Body,
    ): Response {
        val request =
            Request(Method.POST, POST_MESSAGE)
                .header("Authorization", "Bearer ${token.value}")
                .header("content-type", "application/json; charset=utf-8")
                .body(body)
        return invoke(request)
    }

    object Url {
        private const val BASE_URL = "https://slack.com/api"
        const val FILE_UPLOAD = "${BASE_URL}/files.upload"
        const val POST_MESSAGE = "${BASE_URL}/chat.postMessage"
    }
}
