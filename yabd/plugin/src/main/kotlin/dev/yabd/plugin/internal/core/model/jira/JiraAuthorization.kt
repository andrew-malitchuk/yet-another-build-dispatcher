package dev.yabd.plugin.internal.core.model.jira

import dev.yabd.plugin.common.core.regex.isEmail

class JiraAuthorization(
    val email: String?,
    val token: String?,
) {
    init {
        require(!email.isNullOrBlank() && email.isEmail) {
            "Invalid value for `email`"
        }
        require(!token.isNullOrBlank()) {
            "Invalid value for `token`"
        }
    }
}
