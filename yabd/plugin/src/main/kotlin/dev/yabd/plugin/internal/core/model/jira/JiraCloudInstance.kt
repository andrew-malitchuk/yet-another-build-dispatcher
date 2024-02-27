package dev.yabd.plugin.internal.core.model.jira

@JvmInline
value class JiraCloudInstance(val value: String?) {
    init {
        require(!value.isNullOrBlank()) {
            "Invalid value for `jiraCloudInstance`"
        }
    }
}
