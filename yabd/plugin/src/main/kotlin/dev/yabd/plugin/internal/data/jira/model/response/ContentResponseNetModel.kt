package dev.yabd.plugin.internal.data.jira.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class ContentResponseNetModel(
    @SerializedName("content")
    val content: List<CommentContentResponseNetModel>?,
    @SerializedName("type")
    val type: String?,
) : NetModel
