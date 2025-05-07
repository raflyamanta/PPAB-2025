package com.example.week7c.commons

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.week7c.pages.auth.AuthScreen
import com.example.week7c.pages.home.HomeScreen
import com.example.week7c.ui.theme.Week7CTheme

@Composable
fun Application() {
    Week7CTheme(darkTheme = false) {
        Surface{
            var currentScreen by remember { mutableIntStateOf(0) }

            when (currentScreen) {
                0 -> AuthScreen(
                    onLoginSuccess = {
                        currentScreen = 1
                    }
                )
                1 -> HomeScreen(
                    onLogout = {
                        currentScreen = 0
                    }
                )
            }
        }
    }
}