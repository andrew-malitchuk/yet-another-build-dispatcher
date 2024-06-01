package dev.yabd.plugin.common.core.ext

import java.util.Locale

/**
 * Simple method - just makes first letter capitalize; why? cos one from Kotlin is deprecated
 */
fun String.capitalize() =
    replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(Locale.US)
        } else {
            it.toString()
        }
    }

/**
 * Check if input string contains file extension suffix
 *
 * @param extension file's extension without "." ("apk" not ".apk")
 */
fun String.containsExtension(extension: String): Boolean {
    require(!extension.contains(".")) {
        "Please, use file extension without \".\" (f.e. \"apk\" not \".apk\")"
    }
    return this.endsWith(extension)
}
