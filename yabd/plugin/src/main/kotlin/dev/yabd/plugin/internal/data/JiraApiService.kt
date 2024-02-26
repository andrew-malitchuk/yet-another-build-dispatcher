package dev.yabd.plugin.internal.data

import dev.yabd.plugin.internal.usecase.jira.JiraFileUploadUseCase
import dev.yabd.plugin.internal.usecase.jira.model.request.JiraAuthorizationNetModel
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.MultipartFormBody
import org.http4k.core.Response
import java.util.Base64

object JiraApiService {
    fun HttpHandler.leaveComment(
        jiraCloudInstance: String,
        ticket: String,
        authorization: JiraAuthorizationNetModel,
        body: Body,
    ): Response {
        fun getPath(
            jiraCloudInstance: String,
            ticket: String,
        ): String {
            return "${
                Url.BASE_URL.replace(
                    "{${Url.Variables.JIRA_CLOUD_INSTANCE}}",
                    jiraCloudInstance,
                )
            }${
                Url.PATH.replace(
                    "{${Url.Variables.TICKET}}",
                    ticket,
                )
            }"
        }

        val request =
            org.http4k.core.Request(
                method = Method.POST,
                uri = getPath(jiraCloudInstance, ticket),
            )
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header(
                    "Authorization",
                    "Basic " +
                        Base64.getEncoder()
                            .encodeToString("${authorization.email}:${authorization.token}".toByteArray()),
                )
                .body(body)
        return invoke(request)
    }

    fun HttpHandler.uploadFile(
        jiraCloudInstance: String,
        ticket: String,
        authorization: JiraAuthorizationNetModel,
        body: MultipartFormBody,
    ): Response {
        fun getPath(
            jiraCloudInstance: String,
            ticket: String,
        ): String {
            return "${
                JiraFileUploadUseCase.BASE_URL.replace(
                    "{${JiraFileUploadUseCase.Companion.Variables.JIRA_CLOUD_INSTANCE}}",
                    jiraCloudInstance,
                )
            }${
                JiraFileUploadUseCase.PATH.replace(
                    "{${JiraFileUploadUseCase.Companion.Variables.TICKET}}",
                    ticket,
                )
            }"
        }

        val request =
            org.http4k.core.Request(
                method = Method.POST,
                uri = getPath(jiraCloudInstance, ticket),
            )
                .header("X-Atlassian-Token", "nocheck")
                .header(
                    "Authorization",
                    "Basic " +
                        Base64.getEncoder()
                            .encodeToString("${authorization.email}:${authorization.token}".toByteArray()),
                )
                .header("content-type", "multipart/form-data; boundary=${body.boundary}")
                .body(body)
        return invoke(request)
    }

    object Url {
        object Variables {
            const val JIRA_CLOUD_INSTANCE = "jiraCloudInstance"
            const val TICKET = "ticket"
        }

        const val BASE_URL = "https://{${Variables.JIRA_CLOUD_INSTANCE}}"
        const val PATH = "/rest/api/3/issue/{${Variables.TICKET}}/comment"
    }
}
