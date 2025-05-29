package org.cowary

import org.cowary.api.RealApiClient
import org.cowary.bot.BotHandler
import org.cowary.bot.TelegramBot

fun main() {
    val botToken = System.getenv("BOT_TOKEN") ?: error("Не задан BOT_TOKEN в переменных окружения")

    val apiClient = RealApiClient()
    val botHandler = BotHandler(apiClient)
    val bot = TelegramBot(botToken, botHandler)

    bot.startPolling()
    println("Бот запущен!")}