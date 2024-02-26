package dev.yabd.plugin.internal.usecase.jira.model.request

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class AttrsRequestNetModel(
    @SerializedName("display")
    val display: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
) : NetModel
