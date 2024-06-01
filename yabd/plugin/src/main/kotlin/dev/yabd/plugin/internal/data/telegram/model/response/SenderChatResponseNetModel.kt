package dev.yabd.plugin.internal.data.telegram.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class SenderChatResponseNetModel(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?,
) : NetModel
