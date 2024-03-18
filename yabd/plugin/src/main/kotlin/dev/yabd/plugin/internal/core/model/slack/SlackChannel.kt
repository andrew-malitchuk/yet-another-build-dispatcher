package dev.yabd.plugin.internal.core.model.slack

/**
 * Inline value class representing a Slack channel.
 *
 * @param value The value of the Slack channel.
 *
 * @throws IllegalArgumentException if the provided value is null or blank.
 */
@JvmInline
value class SlackChannel(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `SlackChannel`"
        }
    }
}
