package dev.yabd.plugin.internal.data.jira.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class UpdateAuthorResponseNetModel(
    @SerializedName("accountId")
    val accountId: String?,
    @SerializedName("accountType")
    val accountType: String?,
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("avatarUrls")
    val avatarUrls: AvatarUrlsResponseNetModel?,
    @SerializedName("displayName")
    val displayName: String?,
    @SerializedName("emailAddress")
    val emailAddress: String?,
    @SerializedName("self")
    val self: String?,
    @SerializedName("timeZone")
    val timeZone: String?,
) : NetModel
