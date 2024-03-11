package dev.yabd.plugin.internal.data.slack.model.request

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel
import org.http4k.core.Body

data class SlackCommentRequestNetModel(
    @SerializedName("blocks")
    val blocks: List<BlockCommentRequestNetModel?>?,
    @SerializedName("channel")
    val channel: String?,
) : NetModel {
    companion object {
        @Suppress("SwallowedException")
        fun SlackCommentRequestNetModel.toRequestBody(): Body? {
            return try {
                Body(Gson().toJson(this))
            } catch (ex: JsonSyntaxException) {
                null
            }
        }
    }
}
