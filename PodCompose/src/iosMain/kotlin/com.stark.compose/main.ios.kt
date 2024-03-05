package com.stark.compose

import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask


object DarwinPreferencePathProvider: PreferencePathProvider {
    init {
        preferencePathProvider = this
    }
    @OptIn(ExperimentalForeignApi::class)
    override fun getPath(preferenceName: String): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path) + "/${preferenceName}"
    }
}



fun MainViewController() = ComposeUIViewController { App() }