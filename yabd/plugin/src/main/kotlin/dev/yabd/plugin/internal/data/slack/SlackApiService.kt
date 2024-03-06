package dev.yabd.plugin.internal.data.slack

import dev.yabd.plugin.internal.core.model.slack.SlackToken
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.MultipartFormBody
import org.http4k.core.Request
import org.http4k.core.Response

object SlackApiService {
    /**
     * https://api.slack.com/methods/chat.postMessage
     */
    fun HttpHandler.attachFile(
        token: SlackToken,
        body: MultipartFormBody,
    ): Response {
        val request =
            Request(Method.POST, "https://slack.com/api/files.upload")
                .header("Authorization", "Bearer ${token.value}")
                .header("content-type", "multipart/form-data; boundary=${body.boundary}")
                .body(
                    body,
                )
        return invoke(request)
    }

    /**
     * https://api.slack.com/tutorials/tracks/posting-messages-with-curl
     */
    fun HttpHandler.sendMessage(
        token: SlackToken,
        body: Body,
    ): Response {
        val request =
            Request(Method.POST, "https://slack.com/api/chat.postMessage")
                .header("Authorization", "Bearer ${token.value}")
                .header("content-type", "application/json; charset=utf-8")
                .body(body)
        return invoke(request)
    }
}
