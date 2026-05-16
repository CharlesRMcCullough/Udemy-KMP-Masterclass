package com.charlesmccullough.dailypulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.charlesmccullough.dailypulse.articles.ArticlesViewModel
import com.charlesmccullough.dailypulse.screens.AboutScreen
import com.charlesmccullough.dailypulse.screens.ArticlesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val articlesViewModel: ArticlesViewModel by viewModels()
        setContent {
            var isAboutOpen by remember { mutableStateOf(false) }

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isAboutOpen) {
                        BackHandler {
                            isAboutOpen = false
                        }
                        AboutScreen(
                            onUpButtonClick = { isAboutOpen = false }
                        )
                    } else {
                        ArticlesScreen(
                            onAboutButtonClick = { isAboutOpen = true },
                            articlesViewModel = articlesViewModel
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}