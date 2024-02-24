package dev.yabd.plugin.internal.config

@Suppress("ForbiddenComment")
open class JiraCommentConfig {
    // TODO: regex for email
    var email: String? = null
    var token: String? = null
    var filePath: String? = null
    var jiraCloudInstance: String? = null
    var ticket: String? = null
    var comment: String? = null
}
