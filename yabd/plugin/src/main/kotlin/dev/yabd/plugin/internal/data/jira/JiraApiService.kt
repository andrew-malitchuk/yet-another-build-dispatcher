package dev.yabd.plugin.internal.data.jira

import dev.yabd.plugin.internal.core.model.jira.JiraAuthorization
import dev.yabd.plugin.internal.core.model.jira.JiraCloudInstance
import dev.yabd.plugin.internal.core.model.jira.JiraTicket
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.MultipartFormBody
import org.http4k.core.Response
import java.util.Base64

object JiraApiService {
    fun HttpHandler.leaveComment(
        jiraCloudInstance: JiraCloudInstance,
        ticket: JiraTicket,
        authorization: JiraAuthorization,
        body: Body,
    ): Response {
        fun getPath(
            jiraCloudInstance: JiraCloudInstance,
            ticket: JiraTicket,
        ): String {
            return "${
                Url.COMMENT.replace(
                    "{${Url.Variables.JIRA_CLOUD_INSTANCE}}",
                    jiraCloudInstance.value ?: run { throw IllegalArgumentException("`jiraCloudInstance` is invalid") },
                )
            }${
                Url.COMMENT.replace(
                    "{${Url.Variables.TICKET}}",
                    ticket.value ?: run { throw IllegalArgumentException("`ticket` is invalid") },
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
                        Base64
                            .getEncoder()
                            .encodeToString("${authorization.email}:${authorization.token}".toByteArray()),
                )
                .body(body)
        return invoke(request)
    }

    fun HttpHandler.uploadFile(
        jiraCloudInstance: JiraCloudInstance,
        ticket: JiraTicket,
        authorization: JiraAuthorization,
        body: MultipartFormBody,
    ): Response {
        fun getPath(
            jiraCloudInstance: JiraCloudInstance,
            ticket: JiraTicket,
        ): String {
            return "${
                Url.ATTACHMENTS.replace(
                    "{${Url.Variables.JIRA_CLOUD_INSTANCE}}",
                    jiraCloudInstance.value ?: run { throw IllegalArgumentException("`jiraCloudInstance` is invalid") },
                )
            }${
                Url.ATTACHMENTS.replace(
                    "{${Url.Variables.TICKET}}",
                    ticket.value ?: run { throw IllegalArgumentException("`ticket` is invalid") },
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
            const val FILE = "file"
        }

        const val BASE_URL = "https://{${Variables.JIRA_CLOUD_INSTANCE}}"
        const val COMMENT = "${BASE_URL}/rest/api/3/issue/{${Variables.TICKET}}/comment"
        const val ATTACHMENTS = "${BASE_URL}/rest/api/3/issue/{${Url.Variables.TICKET}}/attachments"
    }
}
