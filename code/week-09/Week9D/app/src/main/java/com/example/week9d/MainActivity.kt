package com.example.week9d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week9d.ui.theme.Week9dTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @LoggerServiceImpl1
    @Inject lateinit var logger1: LoggerService
    @LoggerServiceImpl2
    @Inject lateinit var logger2: LoggerService

    @Inject lateinit var loggera: LoggerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week9dTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding), loggera, logger1)
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier, logger1: LoggerService, logger2: LoggerService){
//    val arcticleViewModel: ArticleViewModel = hiltViewModel()
//    ArticleScreen(modifier, arcticleViewModel)
    Column(modifier = modifier) {
        Button(onClick = {logger1.LoggerMethod()}) {
            Text("Tombol")
        }
        Button(onClick = {logger2.LoggerMethod()}) {
            Text("Tombol")
        }
    }
}