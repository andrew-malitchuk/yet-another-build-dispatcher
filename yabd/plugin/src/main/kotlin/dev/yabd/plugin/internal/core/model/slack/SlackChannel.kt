package dev.yabd.plugin.internal.core.model.slack

@JvmInline
value class SlackChannel(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `SlackChannel`"
        }
    }
}
