package com.myapplication

import com.stark.compose.MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.stark.compose.AndroidPreferencePathProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidPreferencePathProvider.derivePath(this)

        setContent {
            MainView()
        }
    }
}