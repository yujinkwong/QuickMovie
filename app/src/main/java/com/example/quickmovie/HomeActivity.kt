// HomeActivity.kt
package com.example.quickmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quickmovie.ui.theme.QuickMovieTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuickMovieTheme {
                // Add any UI for the home screen here
            }
        }
    }
}
