package dev.yabd.plugin.common.core.ext

import com.android.build.gradle.AppExtension
import dev.yabd.plugin.common.model.ArtifactPath
import org.gradle.api.Project

/**
 * Get path to current build artifact according to selected buildVariant
 *
 * @param variant BuildVariant
 *
 * @return wrapper over FS path
 */
fun Project.getBuildPath(variant: String): ArtifactPath? {
    val android = extensions.findByName("android") as? AppExtension
    val path =
        android?.applicationVariants?.firstOrNull {
            it.name == variant
        }?.outputs?.firstOrNull()?.outputFile?.absolutePath
    return if (path.isNullOrBlank()) {
        null
    } else {
        ArtifactPath(path)
    }
}
