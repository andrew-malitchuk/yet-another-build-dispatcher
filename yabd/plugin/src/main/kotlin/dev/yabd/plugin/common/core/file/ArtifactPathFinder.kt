package dev.yabd.plugin.common.core.file

import dev.yabd.plugin.common.core.ext.getBuildPath
import dev.yabd.plugin.common.model.ArtifactPath
import org.gradle.api.GradleException
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project

object ArtifactPathFinder {
    /**
     * Get default build file location
     *
     * @return path to  artifact
     *
     * @throws InvalidUserDataException
     * @throws GradleException
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun detectDefaultArtifactPath(
        project: Project,
        tag: String,
    ): ArtifactPath {
        val buildPath =
            project.getBuildPath(tag)
                ?: run {
                    throw InvalidUserDataException(
                        "Failed to identify build file path; " +
                            "please, try to specify build path manually",
                    )
                }
        return buildPath
    }

    /**
     * Default strategy how to resolve artifact path
     *
     * Strategy:
     * - if [path] is empty - tried to detect file path automatically ([detectDefaultArtifactPath]);
     * - if [path] is valid - wrap it in [ArtifactPath];
     * - otherwise - throw [InvalidUserDataException]
     *
     * @return ArtifactPath
     *
     * @throws InvalidUserDataException
     */
    fun Project.defaultArtifactResolveStrategy(
        path: String?,
        tag: String?,
    ): ArtifactPath {
        require(!tag.isNullOrBlank()) {
            "`tag` is invalid; please, check it"
        }
        return if (path.isNullOrBlank()) {
            detectDefaultArtifactPath(this, tag)
        } else {
            ArtifactPath(path)
        }
    }
}
