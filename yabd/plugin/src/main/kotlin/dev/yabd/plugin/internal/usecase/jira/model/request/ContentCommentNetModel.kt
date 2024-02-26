package dev.yabd.plugin.internal.usecase.jira.model.request

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class ContentCommentNetModel(
    @SerializedName("attrs")
    val attrs: AttrsRequestNetModel?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("type")
    val type: String?,
) : NetModel
