package dev.yabd.plugin.internal.config.jira

@Suppress("ForbiddenComment")
open class JiraAttachBuildConfig {
    // TODO: regex for email
    var email: String? = null
    var token: String? = null
    var filePath: String? = null
    var jiraCloudInstance: String? = null
    var ticket: String? = null
    var comment: String? = null
    var tag: String? = null
    var artifactName: String? = null
}
