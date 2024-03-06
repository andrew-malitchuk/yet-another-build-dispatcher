package dev.yabd.plugin.internal.data.slack.model.request

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class BlockCommentRequestNetModel(
    @SerializedName("text")
    val text: TextRequestNetModel?,
    @SerializedName("type")
    val type: String?,
) : NetModel
