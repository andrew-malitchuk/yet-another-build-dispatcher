package dev.yabd.plugin.internal.core.utils

import dev.yabd.plugin.internal.usecase.jira.model.request.BodyNetModel
import dev.yabd.plugin.internal.usecase.jira.model.request.ContentCommentNetModel
import dev.yabd.plugin.internal.usecase.jira.model.request.ContentNetModel
import dev.yabd.plugin.internal.usecase.jira.model.request.JiraCommentRequestNetModel
import dev.yabd.plugin.internal.usecase.jira.model.response.JiraFileUploadResponseNetModel
import java.lang.IllegalArgumentException

object JiraUtils {
    @Suppress("SwallowedException")
    fun getDownloadLink(response: JiraFileUploadResponseNetModel): String? {
        return try {
            return response.firstOrNull()?.content
        } catch (ex: IllegalArgumentException) {
            null
        }
    }

    fun getDownloadLinkComment(comment: String): JiraCommentRequestNetModel {
        return JiraCommentRequestNetModel(
            body =
                BodyNetModel(
                    content =
                        listOf(
                            ContentNetModel(
                                content =
                                    listOf(
                                        ContentCommentNetModel(
                                            text = comment,
                                            type = "text",
                                        ),
                                    ),
                                type = "paragraph",
                            ),
                        ),
                    type = "doc",
                    version = 1,
                ),
        )
    }

    fun getDownloadLinkComment(
        pattern: String,
        replaceTag: String = Various.COMMENT_TAG,
        jiraFileUpload: JiraFileUploadResponseNetModel,
    ): String {
        require(pattern.contains("{$replaceTag}")) {
            "Please, check if `pattern` contains `replaceTag`"
        }
        require(!jiraFileUpload.firstOrNull()?.content.isNullOrBlank()) {
            "Invalid input data"
        }

        return pattern.replace("{$replaceTag}", jiraFileUpload.firstOrNull()?.content!!)
    }

    object Various {
        const val COMMENT_TAG = "{URL_TO_REPLACE}"
    }
}
