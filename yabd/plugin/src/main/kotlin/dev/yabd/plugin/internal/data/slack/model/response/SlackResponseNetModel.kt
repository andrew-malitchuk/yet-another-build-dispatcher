package dev.yabd.plugin.internal.data.slack.model.response

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel
import org.http4k.core.Response

data class SlackResponseNetModel(
    @SerializedName("file")
    val `file`: SlackFileResponseNetModel?,
    @SerializedName("ok")
    val ok: Boolean?,
) : NetModel {
    companion object {
        @Suppress("SwallowedException")
        fun Response.toSlackResponseNetModel(): SlackResponseNetModel? {
            return try {
                Gson().fromJson(this.bodyString(), SlackResponseNetModel::class.java)
            } catch (ex: JsonSyntaxException) {
                null
            }
        }
    }
}
