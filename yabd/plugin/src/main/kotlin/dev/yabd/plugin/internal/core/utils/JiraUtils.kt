package dev.yabd.plugin.internal.core.utils

import dev.yabd.plugin.internal.data.jira.model.request.BodyNetModel
import dev.yabd.plugin.internal.data.jira.model.request.ContentCommentNetModel
import dev.yabd.plugin.internal.data.jira.model.request.ContentNetModel
import dev.yabd.plugin.internal.data.jira.model.request.JiraCommentRequestNetModel
import dev.yabd.plugin.internal.data.jira.model.response.JiraFileUploadResponseNetModel

object JiraUtils {
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
        require(pattern.contains(replaceTag)) {
            "Please, check if `pattern` contains `replaceTag`"
        }
        require(!jiraFileUpload.firstOrNull()?.content.isNullOrBlank()) {
            "Invalid input data"
        }

        return pattern.replace(replaceTag, jiraFileUpload.firstOrNull()?.content!!)
    }

    object Various {
        const val COMMENT_TAG = "{URL_TO_REPLACE}"
    }
}
