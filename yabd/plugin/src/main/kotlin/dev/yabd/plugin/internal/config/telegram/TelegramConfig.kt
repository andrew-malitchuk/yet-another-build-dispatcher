package dev.yabd.plugin.internal.config.telegram

/**
 * Configuration class for Telegram settings and artifact information.
 */
open class TelegramConfig {
    /**
     * Chat ID where the artifact will be sent.
     */
    var chatId: String? = null

    /**
     * Token for authentication with Telegram.
     */
    var token: String? = null

    /**
     * Defines the path to the build artifact.
     */
    var filePath: String? = null

    /**
     * Build variant, for example, `stageDebug`, `debug`, `release`, etc.
     */
    var tag: String? = null

    /**
     * Custom name for the uploaded artifact.
     */
    var artifactName: String? = null
}
