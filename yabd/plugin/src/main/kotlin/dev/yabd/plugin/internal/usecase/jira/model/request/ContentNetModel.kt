package dev.yabd.plugin.internal.usecase.jira.model.request

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class ContentNetModel(
    @SerializedName("content")
    val content: List<ContentCommentNetModel>?,
    @SerializedName("type")
    val type: String?,
) : NetModel
