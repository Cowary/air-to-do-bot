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
    private val client = HttpClient {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            gson()
        }
    }

    override suspend fun fetchData(): String {
        val response: HttpResponse = client.get("http://localhost:8080/get-task")
        return if (response.status == HttpStatusCode.OK) {
            response.body()
        } else {
            "Ошибка при получении данных: ${response.status}"
        }
    }
}