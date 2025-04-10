package com.example.ppabc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.DoorClosed
import com.composables.icons.lucide.Lucide
import com.example.ppabc.ui.theme.PPABCTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PPABCTheme {
                var selectedNavigationIndex by remember { mutableIntStateOf(0) }
                var isOpen by remember { mutableStateOf(true) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Home")
                            },
                            actions = {
                                IconButton(onClick = {}) {
                                    Icon(Icons.Default.Search, contentDescription = null)
                                }
                                IconButton(onClick = {}) {
                                    Icon(Icons.Default.Add, contentDescription = null)
                                }
                                IconButton(onClick = {}) {
                                    Icon(Icons.Default.MoreVert, contentDescription = null)
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {isOpen = true}) {
                            Icon(Icons.Default.Info, contentDescription = null)
                        }
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = selectedNavigationIndex == 0,
                                icon = {Icon(Icons.Default.Home, contentDescription = null)},
                                onClick = { selectedNavigationIndex = 0 },
                                label = { Text("Home") }
                            )
                            NavigationBarItem(
                                selected = selectedNavigationIndex == 1,
                                icon = {Icon(Icons.Default.Notifications, contentDescription = null)},
                                onClick = { selectedNavigationIndex = 1 },
                                label = { Text("Notifications") }
                            )
                            NavigationBarItem(
                                selected = selectedNavigationIndex == 2,
                                icon = {Icon(Icons.Default.Search, contentDescription = null)},
                                onClick = { selectedNavigationIndex = 2 },
                                label = { Text("Explore") }
                            )
                            NavigationBarItem(
                                selected = selectedNavigationIndex == 3,
                                icon = {Icon(Icons.Default.Person, contentDescription = null)},
                                onClick = { selectedNavigationIndex = 3 },
                                label = { Text("Profile") }
                            )
                        }
                    }
                ) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding), isOpen = isOpen,
                        onClose = {isOpen = false})
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, isOpen: Boolean, onClose: () -> Unit) {
    var scroll = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scroll)) {
        // card update
        AnimatedVisibility(visible = isOpen) {
            Card(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // content
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        // icon
                        Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier
                            .padding(top = 4.dp)
                            .size(18.dp)
                        )

                        // content
                        Column(modifier = Modifier.weight(1f)) {
                            Text("See what's new",
                                style = MaterialTheme.typography.titleMedium)
                            Text("A new version is now available for download on the Play Store.",
                                style = MaterialTheme.typography.bodyMedium)
                        }

                        // action
                        IconButton(onClick = onClose) {
                            Icon(Lucide.DoorClosed, contentDescription = null)
                        }
                    }

                    // button
                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(onClick = {}, modifier = Modifier.align(Alignment.CenterEnd)) {
                            Text("Learn more")
                        }
                    }
                }
            }
        }

        Row(modifier =Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Text("My Work", style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = {}) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
        }
         for(i in 1..10){
             ItemWork("Work $i", image = Icons.Default.Check)
         }
    }
}

@Composable
fun ItemWork(title: String, image: ImageVector){
    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Red),
            contentAlignment = Alignment.Center,
        ){
            Icon(image, contentDescription = null)
        }
        Text(title)
    }
}