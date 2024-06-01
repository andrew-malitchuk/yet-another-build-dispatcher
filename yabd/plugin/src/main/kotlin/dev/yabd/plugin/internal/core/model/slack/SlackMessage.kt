package dev.yabd.plugin.internal.core.model.slack

/**
 * Inline value class representing a Slack message.
 *
 * @param value The value of the Slack message.
 *
 * @throws IllegalArgumentException if the provided value is null or blank.
 */
@JvmInline
value class SlackMessage(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `SlackMessage`"
        }
    }
}
