@file:Suppress("TooGenericExceptionCaught", "SwallowedException")

package dev.yabd.plugin.common.core.regex

import java.io.File
import java.nio.file.InvalidPathException
import java.nio.file.Paths

val String.isEmail: Boolean
    get() = this.matches(Regex(RegexUtils.EMAIL_REGEX))

val String.isJiraCloudInstance: Boolean
    get() = this.matches(Regex(RegexUtils.JIRA_CLOUD_INSTANCE_REGEX))

val String.isJiraTicket: Boolean
    get() = this.matches(Regex(RegexUtils.JIRA_TICKET_REGEX))

val String.isTelegramToken: Boolean
    get() = this.matches(Regex(RegexUtils.TELEGRAM_TOKEN_REGEX))

/**
 * Is [path] contains valid file system path?
 */
@Suppress("TooGenericExceptionCaught", "SwallowedException", "MemberVisibilityCanBePrivate")
val String.isPathValid: Boolean
    get() {
        try {
            Paths.get(this)
        } catch (ex: InvalidPathException) {
            return false
        } catch (ex: NullPointerException) {
            return false
        }
        return true
    }

/**
 * Is file which follow [path] valid?
 */
val String.isFileValid: Boolean
    get() {
        if (!this.isPathValid) return false
        val tempFile = File(this)
        return tempFile.exists() && tempFile.length() > 0
    }

object RegexUtils {
    const val EMAIL_REGEX = "^([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,})$"
    const val JIRA_CLOUD_INSTANCE_REGEX = "^https://[a-zA-Z0-9-]+\\.atlassian\\.net$"
    const val JIRA_TICKET_REGEX = "^[A-Za-z][A-Za-z0-9]*-[0-9]+\$"
    const val TELEGRAM_TOKEN_REGEX = "^[0-9]{9}:[a-zA-Z0-9_-]{35}\$"
}
