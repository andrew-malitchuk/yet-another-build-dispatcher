package dev.yabd.plugin.internal.data.jira.model.request

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class ContentRequestNetModel(
    @SerializedName("content")
    val content: List<ContentCommentRequestNetModel>?,
    @SerializedName("type")
    val type: String?,
) : NetModel
