package com.example.week7a.commons

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week7a.commons.compositions.LocalNavController
import com.example.week7a.commons.compositions.LocalThemeController
import com.example.week7a.commons.compositions.ThemeController
import com.example.week7a.presentation.create_todo.CreateTodoScreen
import com.example.week7a.presentation.home.HomeScreen
import com.example.week7a.presentation.setting.SettingScreen
import com.example.week7a.ui.theme.Week7ATheme

@Composable
fun ComposeApplication(initialTheme: ThemeController) {
    val themeController = remember { mutableStateOf(initialTheme) }

    CompositionLocalProvider(LocalThemeController provides themeController) {
        Week7ATheme(
            darkTheme = when (themeController.value.useSystemTheme) {
                true -> isSystemInDarkTheme()
                else -> themeController.value.useDarkTheme
            }
        ) {
            Surface {
                val navController = rememberNavController()

                CompositionLocalProvider(LocalNavController provides navController) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Home
                    ) {
                        composable<Routes.Home> { HomeScreen() }
                        composable<Routes.CreateTodo> { CreateTodoScreen() }
                        composable<Routes.Setting> { SettingScreen() }
                    }
                }
            }
        }
    }
}
