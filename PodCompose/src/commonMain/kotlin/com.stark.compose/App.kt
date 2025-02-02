package com.stark.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.stark.network.SampleData
import com.stark.network.baseUrl
import com.stark.network.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

suspend fun getSampleData() = httpClient.get("http://localhost:8888/").body<SampleData>()

@Composable
fun App() {
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }

        val (responseText, setResponseText) = remember { mutableStateOf("Server Not Called Yet...") }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(responseText)
            Button(onClick = {
                greetingText = "Hello, ${getPlatformName()}"
                showImage = !showImage

                GlobalScope.launch {
                    val response = getSampleData()
                    setResponseText("${response.author}: ${response.message}")
                }

            }) {
                Text(greetingText)
            }
        }
    }
}

expect fun getPlatformName(): String