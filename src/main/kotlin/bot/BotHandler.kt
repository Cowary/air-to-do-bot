package org.cowary.bot


import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.cowary.api.ApiClient

class BotHandler(private val apiClient: ApiClient) {

    // Обработка команды /start
    fun handleStart() = ActionResponse(
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

    // Обработка нажатия кнопки
    suspend fun handleButtonClick(): ActionResponse {
        return try {
            val apiResponse = apiClient.fetchData()
            ActionResponse("Данные получены:\n$apiResponse")
        } catch (e: Exception) {
            ActionResponse("Ошибка: ${e.message}")
        }
    }
}

// Класс для возврата ответов бота
data class ActionResponse(
    val text: String,
    val replyMarkup: InlineKeyboardMarkup? = null
)