package dev.yabd.plugin.internal.core.utils

import org.gradle.api.logging.Logger

@Suppress("ForbiddenComment")
object LoggerUtils {
    fun Logger.logInfo() {
        lifecycle("\n")
        lifecycle(PLUGIN_INFO)
        lifecycle("\n")
    }

    // TODO: fetch  from gradle.properties
    private const val PLUGIN_INFO = "[YABD] Andrew Malitchuk (2023) ver. 0.0.1-a.1"
}
