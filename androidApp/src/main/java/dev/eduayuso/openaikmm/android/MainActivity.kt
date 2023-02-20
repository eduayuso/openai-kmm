package dev.eduayuso.openaikmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dev.eduayuso.openaikmm.android.navigation.NavigationGraph
import dev.eduayuso.openaikmm.di.KoinViewModels
import org.koin.android.ext.android.inject

class MainActivity: ComponentActivity() {

    private val viewModels: KoinViewModels by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavigationGraph(viewModels)
                }
            }
        }
    }
}

