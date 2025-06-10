package org.cowary.web

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import org.cowary.bot.TelegramBot

class MessageController(private val telegramBot: TelegramBot) {
    private var server: NettyApplicationEngine? = null

    fun start(port: Int = 8080) {
        server = embeddedServer(Netty, port = port) {
            routing {
                post("/send-message") {
                    val params = call.receiveParameters()
                    val userId = params["userId"]?.toLongOrNull()
                    val message = params["message"]

                    if (userId == null || message.isNullOrEmpty()) {
                        call.respond(mapOf("status" to "error", "message" to "Invalid parameters"))
                        return@post
                    }

                    runBlocking {
                        telegramBot.sendMessage(userId, message)
                    }

                    call.respond(mapOf("status" to "success"))
                }
            }
        }.start(wait = false)
    }

    fun stop() {
        server?.stop()
    }
}