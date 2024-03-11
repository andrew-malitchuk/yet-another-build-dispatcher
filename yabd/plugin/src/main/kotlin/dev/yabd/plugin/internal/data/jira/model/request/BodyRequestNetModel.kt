package dev.yabd.plugin.internal.data.jira.model.request

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class BodyRequestNetModel(
    @SerializedName("content")
    val content: List<ContentRequestNetModel>?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("version")
    val version: Int?,
) : NetModel
