package dev.yabd.plugin.internal.core.model.telegram

@JvmInline
value class TelegramMessage(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `TelegramMessage`"
        }
    }
}
