package dev.yabd.plugin.internal.usecase.telegram.model

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class TelegramResultNetModel(
    @SerializedName("author_signature")
    val authorSignature: String?,
    @SerializedName("chat")
    val chatNetModel: ChatNetModel?,
    @SerializedName("date")
    val date: Int?,
    @SerializedName("document")
    val documentNetModel: DocumentNetModel?,
    @SerializedName("has_protected_content")
    val hasProtectedContent: Boolean?,
    @SerializedName("message_id")
    val messageId: Int?,
    @SerializedName("sender_chat")
    val senderChatNetModel: SenderChatNetModel?,
) : NetModel
