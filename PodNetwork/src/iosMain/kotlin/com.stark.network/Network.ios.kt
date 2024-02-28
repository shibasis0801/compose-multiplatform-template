package com.stark.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual val httpClient = HttpClient(Darwin) {
    BodyParser()
}
actual val baseUrl = "http://localhost:${port}"