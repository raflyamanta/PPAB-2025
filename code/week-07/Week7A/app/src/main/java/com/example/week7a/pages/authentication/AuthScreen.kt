package com.example.week7a.pages.authentication

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week7a.commons.LocalNavController
import com.example.week7a.commons.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = viewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is AuthState.Detail.Success -> {
                    Toast.makeText(context, "berhasil!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Routes.Home)
                }

                is AuthState.Detail.Failure ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                else -> Unit
            }
        }
    }

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("rizalanggoro") }
    var password by remember { mutableStateOf("1234") }
    var isLogin by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Auth") })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (isLogin)
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Name") }
                )
            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Username") }
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") }
            )

            Switch(checked = isLogin, onCheckedChange = { isLogin = it })

            Button(
                onClick = {
                    viewModel.login(username, password)
                }
            ) {
                Text("Masuk")
            }
            if (uiState.detail == AuthState.Detail.Loading)
                CircularProgressIndicator()
        }
    }
}