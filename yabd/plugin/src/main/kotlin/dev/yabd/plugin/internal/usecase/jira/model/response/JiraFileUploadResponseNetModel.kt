package dev.yabd.plugin.internal.usecase.jira.model.response

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dev.yabd.plugin.common.model.base.NetModel
import org.http4k.core.Response

class JiraFileUploadResponseNetModel : ArrayList<JiraFileUploadResponseNetModelItem>(), NetModel {
    companion object {
        @Suppress("SwallowedException")
        fun Response.toJiraFileUploadResponseNetModel(): JiraFileUploadResponseNetModel? {
            return try {
                Gson().fromJson(this.bodyString(), JiraFileUploadResponseNetModel::class.java)
            } catch (ex: JsonSyntaxException) {
                null
            }
        }
    }
}
