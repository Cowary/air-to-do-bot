package org.cowary.bot


import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.cowary.api.ApiClient

class BotHandler(private val apiClient: ApiClient, private val allowedUsers: Set<Long>) {

    private fun isUserAllowed(userId: Long): Boolean {
        if (allowedUsers.isEmpty()) return true
        return allowedUsers.contains(userId)
    }

    // Обработка команды /start
    fun handleStart(userId: Long): ActionResponse {
        if (!isUserAllowed(userId)) {
            return ActionResponse("ERROR")
        }

        return ActionResponse(
            text = "Привет! Я тестовый бот. Нажми кнопку для получения данных",
            replyMarkup = InlineKeyboardMarkup.create(
                listOf(
                    InlineKeyboardButton.CallbackData(
                        text = "Получить данные",
                        callbackData = "fetch_data"
                    )
                )
            )
        )
    }

    // Обработка нажатия кнопки
    suspend fun handleButtonClick(userId: Long): ActionResponse {
        if (!isUserAllowed(userId)) {
            return ActionResponse("ERROR")
        }

        return try {
            val apiResponse = apiClient.fetchData()
            ActionResponse("✅ Данные успешно получены:\n$apiResponse")
        } catch (e: Exception) {
            ActionResponse("❌ Ошибка при запросе: ${e.localizedMessage}")
        }
    }
}

data class ActionResponse(
    val text: String,
    val replyMarkup: InlineKeyboardMarkup? = null
)