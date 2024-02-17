package dev.yabd.plugin.internal.core.utils

import dev.yabd.plugin.internal.core.utils.TelegramUtils.Url.START_INDEX
import dev.yabd.plugin.internal.uploader.telegram.model.TelegramResponseNetModel
import java.lang.IllegalArgumentException

object TelegramUtils {
    @Suppress("SwallowedException")
    fun getDownloadLink(response: TelegramResponseNetModel): String? {
        return try {
            val chatId = response.telegramResultNetModel?.chatNetModel?.id?.substring(START_INDEX)
            val messageId = response.telegramResultNetModel?.messageId
            if (chatId == null || messageId == null) return null
            Url.DOWNLOAD_LINK_TEMPLATE.format(chatId, messageId)
        } catch (ex: IllegalArgumentException) {
            null
        }
    }

    object Url {
        const val DOWNLOAD_LINK_TEMPLATE = "https://t.me/c/%s/%d"
        const val START_INDEX = 4
    }
}
