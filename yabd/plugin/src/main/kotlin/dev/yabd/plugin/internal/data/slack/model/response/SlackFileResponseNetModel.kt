package dev.yabd.plugin.internal.data.slack.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class SlackFileResponseNetModel(
    @SerializedName("channels")
    val channels: List<Any?>?,
    @SerializedName("comments_count")
    val commentsCount: Int?,
    @SerializedName("created")
    val created: Int?,
    @SerializedName("display_as_bot")
    val displayAsBot: Boolean?,
    @SerializedName("editable")
    val editable: Boolean?,
    @SerializedName("external_type")
    val externalType: String?,
    @SerializedName("file_access")
    val fileAccess: String?,
    @SerializedName("filetype")
    val filetype: String?,
    @SerializedName("groups")
    val groups: List<String?>?,
    @SerializedName("has_more_shares")
    val hasMoreShares: Boolean?,
    @SerializedName("has_rich_preview")
    val hasRichPreview: Boolean?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("ims")
    val ims: List<Any?>?,
    @SerializedName("is_external")
    val isExternal: Boolean?,
    @SerializedName("is_public")
    val isPublic: Boolean?,
    @SerializedName("is_starred")
    val isStarred: Boolean?,
    @SerializedName("media_display_type")
    val mediaDisplayType: String?,
    @SerializedName("mimetype")
    val mimetype: String?,
    @SerializedName("mode")
    val mode: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("permalink")
    val permalink: String?,
    @SerializedName("permalink_public")
    val permalinkPublic: String?,
    @SerializedName("pretty_type")
    val prettyType: String?,
    @SerializedName("public_url_shared")
    val publicUrlShared: Boolean?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("timestamp")
    val timestamp: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url_private")
    val urlPrivate: String?,
    @SerializedName("url_private_download")
    val urlPrivateDownload: String?,
    @SerializedName("user")
    val user: String?,
    @SerializedName("user_team")
    val userTeam: String?,
    @SerializedName("username")
    val username: String?,
) : NetModel
