package dev.yabd.plugin.internal.data.jira.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class AvatarUrlsResponseNetModel(
    @SerializedName("16x16")
    val x16: String?,
    @SerializedName("24x24")
    val x24: String?,
    @SerializedName("32x32")
    val x32: String?,
    @SerializedName("48x48")
    val x48: String?,
) : NetModel
