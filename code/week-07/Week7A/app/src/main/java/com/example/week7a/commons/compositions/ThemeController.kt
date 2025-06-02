package com.example.week7a.commons.compositions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf

data class ThemeController(
    val useSystemTheme: Boolean = true,
    val useDarkTheme: Boolean = false
)

val LocalThemeController = compositionLocalOf<MutableState<ThemeController>> {
    error("No LocalThemeController provided!")
}