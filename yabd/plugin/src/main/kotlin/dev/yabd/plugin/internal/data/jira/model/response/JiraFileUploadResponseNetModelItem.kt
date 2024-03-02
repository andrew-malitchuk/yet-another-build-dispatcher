package dev.yabd.plugin.internal.data.jira.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class JiraFileUploadResponseNetModelItem(
    @SerializedName("author")
    val author: AuthorNetModel?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("created")
    val created: String?,
    @SerializedName("filename")
    val filename: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("mimeType")
    val mimeType: String?,
    @SerializedName("self")
    val self: String?,
    @SerializedName("size")
    val size: Int?,
) : NetModel
