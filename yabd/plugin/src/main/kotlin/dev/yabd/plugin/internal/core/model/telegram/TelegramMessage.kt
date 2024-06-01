package dev.yabd.plugin.internal.core.model.telegram

/**
 * Inline value class representing a Telegram message.
 *
 * @param value The value of the Telegram message.
 *
 * @throws IllegalArgumentException if the provided value is null or blank.
 */
@JvmInline
value class TelegramMessage(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `TelegramMessage`"
        }
    }
}
