package org.cowary

import org.cowary.api.RealApiClient
import org.cowary.bot.BotHandler
import org.cowary.bot.TelegramBot
import org.cowary.web.MessageController

fun main() {
    val botToken = System.getenv("BOT_TOKEN") ?: error("Не задан BOT_TOKEN в переменных окружения")
    val webPort = System.getenv("WEB_PORT")?.toIntOrNull() ?: 8080

    val allowedUsers = System.getenv("ALLOWED_USERS")
        ?.split(',')
        ?.mapNotNull { it.trim().toLongOrNull() }
        ?.toSet() ?: emptySet()

    val apiClient = RealApiClient()
    val botHandler = BotHandler(apiClient, allowedUsers)
    val bot = TelegramBot(botToken, botHandler)

//    val messageController = MessageController(bot)
//    messageController.start(webPort)

    bot.startPolling()
    println("Бот запущен! REST-сервер слушает порт $webPort")

//    Runtime.getRuntime().addShutdownHook(Thread {
//        println("Завершение работы...")
//        messageController.stop()
//    })
}