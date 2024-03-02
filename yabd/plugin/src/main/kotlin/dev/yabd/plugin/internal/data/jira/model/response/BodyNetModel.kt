package dev.yabd.plugin.internal.data.jira.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class BodyNetModel(
    @SerializedName("content")
    val content: List<ContentNetModel>?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("version")
    val version: Int?,
) : NetModel
