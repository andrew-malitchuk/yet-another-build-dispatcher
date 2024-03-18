package dev.yabd.plugin.internal.core.model.jira

import dev.yabd.plugin.common.core.regex.isJiraTicket

/**
 * Inline value class representing a Jira ticket.
 *
 * @param value The value of the Jira ticket.
 *
 * @throws IllegalArgumentException if the provided value is null, blank, or not in the correct format.
 */
@JvmInline
value class JiraTicket(val value: String?) {
    init {
        require(!value.isNullOrBlank() && value.isJiraTicket) {
            "Invalid value for `jiraTicket`"
        }
    }
}
