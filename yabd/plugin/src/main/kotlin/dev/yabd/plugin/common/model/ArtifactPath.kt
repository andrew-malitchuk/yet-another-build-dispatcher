package dev.yabd.plugin.common.model

import java.io.File
import java.nio.file.InvalidPathException
import java.nio.file.Paths

/**
 * Wrapper over file path which widely used to validate input data.
 *
 * @param path file path
 */
data class ArtifactPath(
    val path: String,
) {
    /**
     * Is [path] contains valid file system path?
     */
    @Suppress("TooGenericExceptionCaught", "SwallowedException", "MemberVisibilityCanBePrivate")
    val isPathValid: Boolean
        get() {
            try {
                Paths.get(path)
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
    val isFileValid: Boolean
        get() {
            if (!this.isPathValid) return false
            val tempFile = File(path)
            return tempFile.exists() && tempFile.length() > 0
        }
}
