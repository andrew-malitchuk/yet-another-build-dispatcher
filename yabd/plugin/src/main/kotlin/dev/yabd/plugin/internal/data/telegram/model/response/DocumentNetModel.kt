package dev.yabd.plugin.internal.data.telegram.model.response

import com.google.gson.annotations.SerializedName
import dev.yabd.plugin.common.model.base.NetModel

data class DocumentNetModel(
    @SerializedName("file_id")
    val fileId: String?,
    @SerializedName("file_name")
    val fileName: String?,
    @SerializedName("file_size")
    val fileSize: Int?,
    @SerializedName("file_unique_id")
    val fileUniqueId: String?,
    @SerializedName("mime_type")
    val mimeType: String?,
) : NetModel
