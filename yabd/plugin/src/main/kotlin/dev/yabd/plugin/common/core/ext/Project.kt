package dev.yabd.plugin.common.core.ext

import com.android.build.gradle.AppExtension
import dev.yabd.plugin.common.model.ArtifactPath
import org.gradle.api.Project

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
