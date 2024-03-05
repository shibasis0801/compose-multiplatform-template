package com.stark.compose

import android.content.Context
import androidx.compose.runtime.Composable
import java.io.File

object AndroidPreferencePathProvider: PreferencePathProvider {
    init {
        preferencePathProvider = this
    }

    private var filesDir: File? = null
    fun derivePath(context: Context) {
        filesDir = context.filesDir
    }

    override fun getPath(preferenceName: String): String {
        return requireNotNull(filesDir?.resolve(preferenceName)?.absolutePath)
    }
}

@Composable fun MainView() = App()
