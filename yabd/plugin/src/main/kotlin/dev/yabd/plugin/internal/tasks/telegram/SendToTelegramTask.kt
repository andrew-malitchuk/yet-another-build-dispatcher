package dev.yabd.plugin.internal.tasks.telegram

import dev.yabd.plugin.common.core.file.ArtifactPathFinder.defaultArtifactResolveStrategy
import dev.yabd.plugin.internal.config.telegram.TelegramConfig
import dev.yabd.plugin.internal.core.model.telegram.TelegramChat
import dev.yabd.plugin.internal.core.model.telegram.TelegramToken
import dev.yabd.plugin.internal.core.utils.TelegramUtils.getDownloadLink
import dev.yabd.plugin.internal.usecase.telegram.SendToTelegramUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

/**
 * Task for uploading files to Telegram.
 */
abstract class SendToTelegramTask : DefaultTask() {
    init {
        group = "Telegram"
        description = "Telegram file uploader"
    }

    /**
     * Configuration for sending files to Telegram.
     */
    @get:Input
    abstract val telegramConfig: Property<TelegramConfig>

    /**
     * Boolean option to enable debug output.
     */
    @Option(description = DEBUG_DESCRIPTION, option = DEBUG_OUTPUT)
    @get:Input
    var debugOutput: Boolean = false

    /**
     * Action method for the task.
     */
    @Suppress("NestedBlockDepth")
    @TaskAction
    fun action() {
        with(telegramConfig.get()) {
            val artifactPath = project.defaultArtifactResolveStrategy(filePath, tag)
            if (debugOutput) {
                logger.apply {
                    lifecycle("send-to-telegram  |  buildVariant  : $tag")
                    lifecycle("send-to-telegram  |  chatId        : $chatId")
                    lifecycle("send-to-telegram  |  token         : $token")
                    lifecycle("send-to-telegram  |  filePath      : ${artifactPath.value}")
                    artifactName?.let {
                        lifecycle("send-to-telegram  |  artifactName  : $artifactName")
                    }
                }
            }
            val response =
                SendToTelegramUseCase(
                    chatId = TelegramChat(chatId),
                    token = TelegramToken(token),
                    artifactPath = artifactPath,
                    artifactName = artifactName,
                ).invoke()
            if (debugOutput) {
                logger.apply {
                    response?.let {
                        lifecycle("send-to-telegram  |  link          : ${getDownloadLink(it)}")
                    }
                }
            }
        }
    }

    companion object {
        const val DEBUG_OUTPUT = "debugOutput"
        const val DEBUG_DESCRIPTION = "debugOutput"
    }
}
