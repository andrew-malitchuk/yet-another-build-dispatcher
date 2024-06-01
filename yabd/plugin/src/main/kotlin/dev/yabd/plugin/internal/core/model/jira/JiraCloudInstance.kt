package dev.yabd.plugin.internal.core.model.jira

/**
 * Inline value class representing a Jira cloud instance.
 *
 * @param value The value of the Jira cloud instance.
 *
 * @throws IllegalArgumentException if the provided value is null or blank.
 */
@JvmInline
value class JiraCloudInstance(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `jiraCloudInstance`"
        }
    }
}
