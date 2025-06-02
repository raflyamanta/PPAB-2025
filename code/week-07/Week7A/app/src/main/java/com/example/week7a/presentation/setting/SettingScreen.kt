package com.example.week7a.presentation.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.week7a.commons.compositions.LocalNavController
import com.example.week7a.commons.compositions.LocalThemeController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val themeController = LocalThemeController.current

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                title = { Text("Pengaturan") },
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            ListItem(
                headlineContent = { Text("Tema Sistem") },
                supportingContent = { Text("Gunakan tema sesuai sistem saat ini") },
                trailingContent = {
                    Switch(
                        checked = themeController.value.useSystemTheme,
                        onCheckedChange = {
                            themeController.value = themeController.value.copy(useSystemTheme = it)
                            viewModel.changeTheme(themeController.value)
                        }
                    )
                }
            )
            if (!themeController.value.useSystemTheme)
                ListItem(
                    headlineContent = { Text("Tema Gelap") },
                    supportingContent = { Text("Gunakan pengaturan tema gelap") },
                    trailingContent = {
                        Switch(
                            checked = themeController.value.useDarkTheme,
                            onCheckedChange = {
                                themeController.value =
                                    themeController.value.copy(useDarkTheme = it)
                                viewModel.changeTheme(themeController.value)
                            }
                        )
                    }
                )
        }
    }
}