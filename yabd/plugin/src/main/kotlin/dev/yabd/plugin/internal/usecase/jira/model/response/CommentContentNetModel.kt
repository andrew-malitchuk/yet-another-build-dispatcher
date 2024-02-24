package dev.yabd.plugin.internal.usecase.jira.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class CommentContentNetModel(
    @SerializedName("text")
    val text: String?,
    @SerializedName("type")
    val type: String?,
) : NetModel