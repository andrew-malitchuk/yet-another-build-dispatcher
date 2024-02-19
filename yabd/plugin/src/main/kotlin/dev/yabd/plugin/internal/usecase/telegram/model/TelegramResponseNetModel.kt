package dev.yabd.plugin.internal.usecase.telegram.model

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel
import org.http4k.core.Response

data class TelegramResponseNetModel(
    @SerializedName("ok")
    val ok: Boolean?,
    @SerializedName("result")
    val telegramResultNetModel: TelegramResultNetModel?,
) : NetModel {
    companion object {
        @Suppress("SwallowedException")
        fun Response.toTelegramResponseNetModel(): TelegramResponseNetModel? {
            return try {
                Gson().fromJson(this.bodyString(), TelegramResponseNetModel::class.java)
            } catch (ex: JsonSyntaxException) {
                null
            }
        }
    }
}
