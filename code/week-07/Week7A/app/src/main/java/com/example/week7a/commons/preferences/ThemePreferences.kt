package com.example.week7a.commons.preferences

import android.content.Context
import com.example.week7a.commons.compositions.ThemeController
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val preferences = context.getSharedPreferences("theme_pref", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun setTheme(controller: ThemeController) {
        preferences.edit().apply {
            putString("theme", gson.toJson(controller))
            apply()
        }
    }

    fun getTheme(): ThemeController {
        val themeStr = preferences.getString("theme", null)
        if (themeStr != null)
            return gson.fromJson(themeStr, ThemeController::class.java)
        
        return ThemeController(
            useSystemTheme = true,
            useDarkTheme = false
        )
    }
}