package com.stark.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual val httpClient = HttpClient(OkHttp) {
    BodyParser()
}
actual val baseUrl = "http://10.0.2.2:${port}"