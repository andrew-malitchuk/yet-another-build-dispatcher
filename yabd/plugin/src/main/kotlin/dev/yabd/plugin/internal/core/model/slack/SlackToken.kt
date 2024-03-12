package dev.yabd.plugin.internal.core.model.slack

/**
 * Inline value class representing a Slack token.
 *
 * @param value The value of the Slack token.
 *
 * @throws IllegalArgumentException if the provided value is null or blank.
 */
@JvmInline
value class SlackToken(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `SlackToken`"
        }
    }
}
