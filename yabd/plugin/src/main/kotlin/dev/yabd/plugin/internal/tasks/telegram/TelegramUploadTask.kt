package dev.yabd.plugin.internal.tasks.telegram

import dev.yabd.plugin.common.core.file.ArtifactPathFinder.defaultArtifactResolveStrategy
import dev.yabd.plugin.internal.config.telegram.TelegramConfig
import dev.yabd.plugin.internal.core.model.telegram.TelegramChatId
import dev.yabd.plugin.internal.core.model.telegram.TelegramToken
import dev.yabd.plugin.internal.core.utils.TelegramUtils.getDownloadLink
import dev.yabd.plugin.internal.usecase.telegram.TelegramFileUploadUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class TelegramUploadTask : DefaultTask() {
    init {
        group = "Telegram"
        description = "Telegram file uploader"
    }

    @get:Input
    abstract val telegramConfig: Property<TelegramConfig>

    @Option(description = DEBUG_DESCRIPTION, option = DEBUG_OUTPUT)
    @get:Input
    var debugOutput: Boolean = false

    @Suppress("NestedBlockDepth")
    @TaskAction
    fun action() {
        with(telegramConfig.get()) {
            val artifactPath = project.defaultArtifactResolveStrategy(filePath, tag)
            if (debugOutput) {
                logger.apply {
                    lifecycle("telegram-config  |   buildVariant    : $tag")
                    lifecycle("telegram-config  |   chatId          : $chatId")
                    lifecycle("telegram-config  |   token           : $token")
                    lifecycle("telegram-config  |   filePath        : ${artifactPath.value}")
                    artifactName?.let {
                        lifecycle("telegram-config  |   artifactName    : $artifactName")
                    }
                }
            }
            val response =
                TelegramFileUploadUseCase(
                    chatId = TelegramChatId(chatId),
                    token = TelegramToken(token),
                    artifactPath = artifactPath,
                    artifactName = artifactName,
                ).invoke()
            if (debugOutput) {
                logger.apply {
                    response?.let {
                        lifecycle("telegram-config  |   link            : ${getDownloadLink(it)}")
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
