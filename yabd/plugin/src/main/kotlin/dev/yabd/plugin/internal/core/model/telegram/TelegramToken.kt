package dev.yabd.plugin.internal.core.model.telegram

/**
 * Inline value class representing a Telegram token.
 *
 * @param value The value of the Telegram token.
 *
 * @throws IllegalArgumentException if the provided value is null or blank.
 */
@JvmInline
value class TelegramToken(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `TelegramToken`"
        }
    }
}
