package com.stark.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.stark.network.baseUrl
import com.stark.network.httpClient
//import com.stark.network.httpClient
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }

        // const [responseText, setResponseText] = use StateOf("Server Not Called Yet...")
        val (responseText, setResponseText) = remember { mutableStateOf("Server Not Called Yet...") }


        LaunchedEffect(Unit) {
            httpClient
//            httpClient.get<JsonResponse>("${baseUrl}/get")
        }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(responseText)
            Button(onClick = {
                greetingText = "Hello, ${getPlatformName()}"
                showImage = !showImage
            }) {
                Text(greetingText)
            }
            AnimatedVisibility(showImage) {
                Image(
                    painterResource("compose-multiplatform.xml"),
                    contentDescription = "Compose Multiplatform icon"
                )
            }
        }
    }
}

expect fun getPlatformName(): String