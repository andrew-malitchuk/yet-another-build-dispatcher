package dev.yabd.plugin.internal.core.model.telegram

/**
 * Inline value class representing a Telegram chat ID.
 *
 * @param value The value of the Telegram chat ID.
 *
 * @throws IllegalArgumentException if the provided value is null or blank.
 */
@JvmInline
value class TelegramChat(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `TelegramChatId`"
        }
    }
}
