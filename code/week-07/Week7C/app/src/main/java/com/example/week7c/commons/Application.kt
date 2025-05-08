package com.example.week7c.commons

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.week7c.pages.auth.AuthScreen
import com.example.week7c.pages.home.HomeScreen
import com.example.week7c.ui.theme.Week7CTheme

@Composable
fun Application() {
    Week7CTheme(darkTheme = false) {
        val navController = rememberNavController()

        CompositionLocalProvider(LocalNavController provides navController) {
            Surface {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Auth,
                ) {
                    composable<Routes.Auth> {
                        AuthScreen()
                    }

                    composable<Routes.Home> {
                        val data = it.toRoute<Routes.Home>()
                        HomeScreen(name = data.name)
                    }
                }
            }
        }
    }
}