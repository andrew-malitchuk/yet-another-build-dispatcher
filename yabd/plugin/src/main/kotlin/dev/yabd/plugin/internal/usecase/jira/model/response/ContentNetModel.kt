package dev.yabd.plugin.internal.usecase.jira.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class ContentNetModel(
    @SerializedName("content")
    val content: List<CommentContentNetModel>?,
    @SerializedName("type")
    val type: String?,
) : NetModel
