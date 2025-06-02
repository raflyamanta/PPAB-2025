package com.example.week7a.presentation.setting

import androidx.lifecycle.ViewModel
import com.example.week7a.commons.compositions.ThemeController
import com.example.week7a.commons.preferences.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : ViewModel() {
    fun changeTheme(controller: ThemeController) = themePreferences.setTheme(controller)
}