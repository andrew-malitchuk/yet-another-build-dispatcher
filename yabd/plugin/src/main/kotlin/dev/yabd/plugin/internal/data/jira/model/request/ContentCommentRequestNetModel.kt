package dev.yabd.plugin.internal.data.jira.model.request

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class ContentCommentRequestNetModel(
    @SerializedName("text")
    val text: String?,
    @SerializedName("type")
    val type: String?,
) : NetModel
