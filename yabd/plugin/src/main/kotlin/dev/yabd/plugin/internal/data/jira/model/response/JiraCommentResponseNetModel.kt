package dev.yabd.plugin.internal.data.jira.model.response

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel
import org.http4k.core.Response

data class JiraCommentResponseNetModel(
    @SerializedName("author")
    val author: AuthorResponseNetModel?,
    @SerializedName("body")
    val body: BodyResponseNetModel?,
    @SerializedName("created")
    val created: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("jsdPublic")
    val jsdPublic: Boolean?,
    @SerializedName("self")
    val self: String?,
    @SerializedName("updateAuthor")
    val updateAuthor: UpdateAuthorResponseNetModel?,
    @SerializedName("updated")
    val updated: String?,
) : NetModel {
    companion object {
        @Suppress("SwallowedException")
        fun Response.toJiraCommentResponseNetModel(): JiraCommentResponseNetModel? {
            return try {
                Gson().fromJson(this.bodyString(), JiraCommentResponseNetModel::class.java)
            } catch (ex: JsonSyntaxException) {
                null
            }
        }
    }
}
