package org.cowary.bot

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import kotlinx.coroutines.runBlocking

class TelegramBot(
    token: String,
    private val handler: BotHandler
) {
    private val bot = bot {
        this.token = token
        dispatch {
            command("start") {
                val response = handler.handleStart()
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = response.text,
                    replyMarkup = response.replyMarkup
                )
            }

            callbackQuery("fetch_data") {
                // Добавляем корутину для вызова suspend-функции
                runBlocking {
                    val response = handler.handleButtonClick()
                    bot.sendMessage(
                        chatId = ChatId.fromId(callbackQuery.message?.chat?.id ?: return@runBlocking),
                        text = response.text
                    )
                }
            }
        }
    }

    fun startPolling() {
        bot.startPolling()
    }
}