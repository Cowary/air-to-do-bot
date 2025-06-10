package org.cowary.api


import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*

interface ApiClient {
    suspend fun fetchData(): String
}

class RealApiClient : ApiClient {
    private val urlApi = System.getenv("API_URL") ?: error("Не задан API_URL в переменных окружения")

    private val client = HttpClient {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            gson()
        }
    }

    override suspend fun fetchData(): String {
        val response: HttpResponse = client.get(urlApi)
        return if (response.status == HttpStatusCode.OK) {
            println("Response body: $response")
            response.body()
        } else {
            println("Ошибка при получении данных " + response.status)
            "Ошибка при получении данных: ${response.status}"
        }
    }
}