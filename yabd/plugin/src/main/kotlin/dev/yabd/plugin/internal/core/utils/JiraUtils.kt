package dev.yabd.plugin.internal.core.utils

import dev.yabd.plugin.internal.data.jira.model.request.BodyRequestNetModel
import dev.yabd.plugin.internal.data.jira.model.request.ContentCommentrequestNetModel
import dev.yabd.plugin.internal.data.jira.model.request.ContentRequestNetModel
import dev.yabd.plugin.internal.data.jira.model.request.JiraCommentRequestNetModel
import dev.yabd.plugin.internal.data.jira.model.response.JiraFileUploadResponseNetModel

object JiraUtils {
    fun getDownloadLinkComment(comment: String): JiraCommentRequestNetModel {
        return JiraCommentRequestNetModel(
            body =
                BodyRequestNetModel(
                    content =
                        listOf(
                            ContentRequestNetModel(
                                content =
                                    listOf(
                                        ContentCommentrequestNetModel(
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
