package dev.yabd.plugin.internal.data.jira.model.request

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel
import org.http4k.core.Body

data class JiraCommentRequestNetModel(
    @SerializedName("body")
    val body: BodyNetModel?,
) : NetModel {
    companion object {
        @Suppress("SwallowedException")
        fun JiraCommentRequestNetModel.toRequestBody(): Body? {
            return try {
                Body(Gson().toJson(this))
            } catch (ex: JsonSyntaxException) {
                null
            }
        }
    }
}
