package dev.yabd.plugin.internal.data.telegram.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class TelegramResultResponseNetModel(
    @SerializedName("author_signature")
    val authorSignature: String?,
    @SerializedName("chat")
    val chatNetModel: ChatResponseNetModel?,
    @SerializedName("date")
    val date: Int?,
    @SerializedName("document")
    val documentNetModel: DocumentResponseNetModel?,
    @SerializedName("has_protected_content")
    val hasProtectedContent: Boolean?,
    @SerializedName("message_id")
    val messageId: Int?,
    @SerializedName("sender_chat")
    val senderChatNetModel: SenderChatResponseNetModel?,
) : NetModel
