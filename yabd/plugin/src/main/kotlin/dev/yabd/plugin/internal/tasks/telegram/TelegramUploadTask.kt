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

    @get:Input
    var foobar: Boolean = false

//    // Expose a setter method to set the value of the property
//    fun setMyArgument(value: Boolean) {
//        this.myArgument = value
//    }
//
//    fun getMyArgument() = this.myArgument

    @TaskAction
    fun action() {
        with(telegramConfig.get()) {
            val artifactPath = project.defaultArtifactResolveStrategy(filePath, tag)

            logger.apply {
                lifecycle("telegram-config  |   isDebuggable    : $foobar")
//                lifecycle("telegram-config  |   buildVariant    : $tag")
//                lifecycle("telegram-config  |   chatId          : $chatId")
//                lifecycle("telegram-config  |   token           : $token")
//                lifecycle("telegram-config  |   filePath        : ${artifactPath.value}")
//                artifactName?.let {
//                    lifecycle("telegram-config  |   artifactName    : $artifactName")
//                }
//                val response =
//                    TelegramFileUploadUseCase(
//                        chatId = TelegramChatId(chatId),
//                        token = TelegramToken(token),
//                        artifactPath = artifactPath,
//                        artifactName = artifactName,
//                    ).invoke()
//                response?.let {
//                    lifecycle("telegram-config  |   link            : ${getDownloadLink(it)}")
//                }
            }
        }
    }

    companion object {
        const val IS_DEBUGGABLE = "isDebuggable"
    }
}
