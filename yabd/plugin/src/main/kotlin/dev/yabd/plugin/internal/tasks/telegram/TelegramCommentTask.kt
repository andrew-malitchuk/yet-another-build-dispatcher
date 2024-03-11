package dev.yabd.plugin.internal.tasks.telegram

import dev.yabd.plugin.internal.config.telegram.TelegramConfig
import dev.yabd.plugin.internal.core.model.telegram.TelegramChat
import dev.yabd.plugin.internal.core.model.telegram.TelegramMessage
import dev.yabd.plugin.internal.core.model.telegram.TelegramToken
import dev.yabd.plugin.internal.usecase.telegram.TelegramCommentUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class TelegramCommentTask : DefaultTask() {
    init {
        group = "Telegram"
        description = "Telegram send message"
    }

    @get:Input
    abstract val telegramConfig: Property<TelegramConfig>

    @Option(description = DEBUG_DESCRIPTION, option = DEBUG_OUTPUT)
    @get:Input
    var debugOutput: Boolean = false

    @Option(description = MESSAGE, option = MESSAGE)
    @get:Input
    var message: String = ""

    @Suppress("NestedBlockDepth")
    @TaskAction
    fun action() {
        with(telegramConfig.get()) {
            if (debugOutput) {
                logger.apply {
                    lifecycle("telegram-config  |   buildVariant    : $tag")
                    lifecycle("telegram-config  |   chatId          : $chatId")
                    lifecycle("telegram-config  |   token           : $token")
                    lifecycle("telegram-config  |   message        : $message")
                }
            }
            TelegramCommentUseCase(
                chatId = TelegramChat(chatId),
                token = TelegramToken(token),
                message = TelegramMessage(message),
            ).invoke()
        }
    }

    companion object {
        const val DEBUG_OUTPUT = "debugOutput"
        const val DEBUG_DESCRIPTION = "debugOutput"
        const val MESSAGE = "message"
    }
}
