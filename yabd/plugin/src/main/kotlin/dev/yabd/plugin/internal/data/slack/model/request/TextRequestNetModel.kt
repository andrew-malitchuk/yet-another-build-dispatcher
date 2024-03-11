package dev.yabd.plugin.internal.data.slack.model.request

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class TextRequestNetModel(
    @SerializedName("text")
    val text: String?,
    @SerializedName("type")
    val type: String?,
) : NetModel
