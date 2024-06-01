package dev.yabd.plugin.internal.data.jira.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class BodyResponseNetModel(
    @SerializedName("content")
    val content: List<ContentResponseNetModel>?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("version")
    val version: Int?,
) : NetModel
