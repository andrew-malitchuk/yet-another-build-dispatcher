package dev.yabd.plugin.internal.core.model.telegram

@JvmInline
value class TelegramToken(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `TelegramToken`"
        }
    }
}
