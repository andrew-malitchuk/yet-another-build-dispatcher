package dev.yabd.plugin.internal.core.model.jira

import dev.yabd.plugin.common.core.regex.isEmail

/**
 * Class representing authorization credentials for Jira.
 *
 * @param email Email associated with the Jira account.
 * @param token Token for authentication with Jira.
 *
 * @throws IllegalArgumentException if the provided email or token is null, blank, or invalid.
 */
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
