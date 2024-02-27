package dev.yabd.plugin.internal.core.model.jira

import dev.yabd.plugin.common.core.regex.isJiraTicket

@JvmInline
value class JiraTicket(val value: String?) {
    init {
        require(!value.isNullOrBlank() && value.isJiraTicket) {
            "Invalid value for `jiraTicket`"
        }
    }
}
