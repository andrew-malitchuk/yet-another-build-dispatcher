package dev.yabd.plugin.internal.core.utils

import dev.yabd.plugin.internal.data.jira.model.request.BodyRequestNetModel
import dev.yabd.plugin.internal.data.jira.model.request.ContentCommentrequestNetModel
import dev.yabd.plugin.internal.data.jira.model.request.ContentRequestNetModel
import dev.yabd.plugin.internal.data.jira.model.request.JiraCommentRequestNetModel
import dev.yabd.plugin.internal.data.jira.model.response.JiraFileUploadResponseNetModel

/**
 * Utility object providing methods for Jira-related operations.
 */
object JiraUtils {
    /**
     * Generates a Jira comment request model containing a download link comment.
     *
     * @param comment The comment to include in the download link.
     *
     * @return A JiraCommentRequestNetModel object representing the comment request model.
     */
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

    /**
     * Generates a download link comment by replacing a tag in the pattern with the provided
     * content from a Jira file upload.
     *
     * @param pattern The pattern containing the tag to replace.
     * @param replaceTag The tag to replace in the pattern.
     * @param jiraFileUpload The response containing the uploaded file information.
     *
     * @return The download link comment with the tag replaced by the file content.
     *
     * @throws IllegalArgumentException if the pattern does not contain the replaceTag or if the
     * jiraFileUpload contains invalid data.
     */
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

    /**
     * Object containing various constants used in JiraUtils.
     */
    object Various {
        const val COMMENT_TAG = "{URL_TO_REPLACE}"
    }
}
