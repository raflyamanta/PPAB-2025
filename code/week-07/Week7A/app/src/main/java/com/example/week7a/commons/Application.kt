package com.example.week7a.commons

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week7a.pages.authentication.AuthScreen
import com.example.week7a.pages.home.HomeScreen
import com.example.week7a.ui.theme.Week7ATheme

@Composable
fun Application() {
    Week7ATheme(darkTheme = false) {
        Surface {
            val navController = rememberNavController()

            CompositionLocalProvider(LocalNavController provides navController) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Authentication
                ) {
                    composable<Routes.Authentication> {
                        AuthScreen()
                    }

                    composable<Routes.Home> {
                        HomeScreen()
                    }
                }
            }
        }
    }
}