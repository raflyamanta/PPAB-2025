package com.example.week7a.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.week7a.commons.Routes
import com.example.week7a.commons.compositions.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.detail) {
        with(uiState.detail) {
            when (this) {
                is HomeState.Detail.Error -> Toast.makeText(
                    context, message, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo") },
                actions = {
                    IconButton(
                        onClick = { viewModel.getAllTodos() },
                    ) {
                        Icon(Icons.Rounded.Refresh, contentDescription = null)
                    }
                    IconButton(
                        onClick = { navController.navigate(Routes.Setting) }
                    ) {
                        Icon(Icons.Rounded.Settings, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.CreateTodo) }) {
                Icon(Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
//            if (uiState.detail is HomeState.Detail.Loading)
//                item {
//                    Box(modifier = Modifier.fillMaxWidth()) {
//                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//                    }
//                }

            items(uiState.todos) { todo ->
                Card(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Checkbox di kiri
                        Checkbox(
                            checked = todo.status,
                            onCheckedChange = { isChecked ->
                                viewModel.updateTodo(todo, isChecked)
                            }
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        ) {
                            Text(
                                todo.title,
                                style = MaterialTheme.typography.titleMedium,
                                textDecoration = if (todo.status) TextDecoration.LineThrough else TextDecoration.None
                            )
                            if (todo.description != null)
                                Text(
                                    todo.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    textDecoration = if (todo.status) TextDecoration.LineThrough else TextDecoration.None
                                )
                        }

                        // Tombol delete di kanan
                        IconButton(
                            onClick = { viewModel.deleteTodo(todo) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Todo"
                            )
                        }
                    }
                }
            }
        }
    }
}