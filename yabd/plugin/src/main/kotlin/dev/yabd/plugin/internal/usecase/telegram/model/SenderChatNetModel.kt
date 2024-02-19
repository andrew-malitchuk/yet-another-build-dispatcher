package dev.yabd.plugin.internal.usecase.telegram.model

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class SenderChatNetModel(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?,
) : NetModel
