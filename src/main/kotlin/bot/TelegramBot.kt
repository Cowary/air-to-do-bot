package org.cowary.bot

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.logging.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TelegramBot(
    token: String,
    val handler: BotHandler
) {
    val bot = bot {
        logLevel = LogLevel.All()
        this.token = token
        dispatch {
            command("start") {
                val userId = message.chat.id
                val response = handler.handleStart(userId)
                bot.sendMessage(
                    chatId = ChatId.fromId(userId),
                    text = response.text,
                    replyMarkup = response.replyMarkup
                )
            }

            callbackQuery("fetch_data") {
                println("fetch_data")
                val userId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                runBlocking {
                    val response = handler.handleButtonClick(userId)
                    bot.sendMessage(
                        chatId = ChatId.fromId(userId),
                        text = response.text
                    )
                }
            }
        }
    }

    fun startPolling() {
        bot.startPolling()
//        bot {
//            logLevel = LogLevel.All()
//        }
    }

    fun sendMessage(userId: Long, text: String) {
        bot.sendMessage(
            chatId = ChatId.fromId(userId),
            text = text
        )
    }

}