package dev.yabd.plugin.internal.usecase.slack

import dev.yabd.plugin.common.core.ext.containsExtension
import dev.yabd.plugin.common.model.ArtifactPath
import dev.yabd.plugin.internal.core.model.slack.SlackChannel
import dev.yabd.plugin.internal.core.model.slack.SlackToken
import dev.yabd.plugin.internal.data.slack.SlackApiService.foo
import dev.yabd.plugin.internal.data.slack.model.response.SlackResponseNetModel
import dev.yabd.plugin.internal.data.slack.model.response.SlackResponseNetModel.Companion.toSlackResponseNetModel
import dev.yabd.plugin.internal.usecase.base.UseCase
import org.gradle.api.GradleException
import org.http4k.client.ApacheClient
import org.http4k.core.ContentType
import org.http4k.core.MultipartFormBody
import org.http4k.lens.MultipartFormFile
import java.io.File

class SlackFileUploadUseCase(
    private val token: SlackToken,
    private val channel: SlackChannel,
    private val artifactPath: ArtifactPath,
    private val artifactName: String? = null,
) : UseCase() {
    override fun invoke(): SlackResponseNetModel? {
        var file = File(artifactPath.value)

        if (!artifactName.isNullOrBlank()) {
            if (artifactName.containsExtension("apk")) {
                val newFile = File(file.parentFile.path + "/$artifactName")
                file.renameTo(newFile)
                file = newFile
            }
        }

        val body =
            MultipartFormBody()
                .plus(
                    "file" to
                        MultipartFormFile(
                            file.name,
                            ContentType.OCTET_STREAM,
                            file.inputStream(),
                        ),
                )
                .plus(
                    "initial_comment" to "foobar",
                )
                .plus(
                    "channels" to "${channel.value}",
                )
        val response = ApacheClient().foo(token, body)

        return if (response.status.successful) {
            response.toSlackResponseNetModel()
        } else {
            throw GradleException(
                "SlackUploader |   failed to upload build: " +
                    "${response.status.code}: ${response.status.description} (${response.bodyString()})",
            )
        }
    }
}
