package dev.yabd.plugin.internal.core.model.telegram

@JvmInline
value class TelegramChatId(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `TelegramChatId`"
        }
    }
}
