package com.stark.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

expect val httpClient: HttpClient
expect val baseUrl: String
const val port = 8888

fun<T : HttpClientEngineConfig> HttpClientConfig<T>.BodyParser() {
    install(ContentNegotiation) {
        json()
    }
}

enum class StatusCode(val code: Int) {
    SUCCESS(200),
    ERROR_CLIENT(400),
    ERROR_RATE_LIMIT(429),
    ERROR_SERVER(500)
}

data class Response(
    val body: String,
    val statusCode: StatusCode = StatusCode.SUCCESS
) {
    companion object {
        inline fun<reified T> success(
            data: T,
            statusCode: StatusCode = StatusCode.SUCCESS,
        ) = Response(Json.encodeToString<T>(data), statusCode)

        inline fun<reified T> error(
            error: T,
            statusCode: StatusCode = StatusCode.ERROR_SERVER,
        ) = Response(Json.encodeToString<T>(error), statusCode)
    }
}

