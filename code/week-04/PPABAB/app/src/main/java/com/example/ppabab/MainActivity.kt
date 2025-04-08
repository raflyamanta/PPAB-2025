package com.example.ppabab

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Camera
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Globe
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MessageSquare
import com.composables.icons.lucide.Phone
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.User
import com.composables.icons.lucide.Users
import com.example.ppabab.ui.theme.PPABABTheme

private data class NavItem(
    val label: String,
    val icon: ImageVector
)
private val navItems = listOf(
    NavItem("Chat", Lucide.MessageSquare),
    NavItem("Pembaruan", Lucide.Globe),
    NavItem("Komunitas", Lucide.Users),
    NavItem("Panggilan", Lucide.Phone),
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PPABABTheme {
                var selectedNavigationIndex by remember { mutableIntStateOf(0) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                       Column {
                           TopAppBar(
                               title = {
                                   Text(
                                       text = "WhatsApp",
                                       fontWeight = FontWeight.Bold
                                   )
                               },
                               actions = {
                                   when (selectedNavigationIndex) {
                                       0 -> IconButton(onClick = {}) {
                                            Icon(Lucide.Camera, contentDescription = null)
                                       }
                                       1 -> IconButton(onClick = {}) {
                                           Icon(Lucide.Search, contentDescription = null)
                                       }
                                   }
                                   IconButton(onClick = {}) {
                                       Icon(Lucide.EllipsisVertical, contentDescription = null)
                                   }
                               }
                           )
                           HorizontalDivider()
                       }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {}) {
                            Icon(Lucide.Plus, contentDescription = null)
                        }
                    },
                    bottomBar = {
                        NavigationBar {
                            navItems.mapIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedNavigationIndex == index,
                                    onClick = {selectedNavigationIndex = index},
                                    icon = {
                                        Icon(
                                            item.icon,
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(text = item.label) }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    when (selectedNavigationIndex) {
                        0 -> ChatScreen(modifier = Modifier.padding(innerPadding))
                        1 -> StatusScreen(modifier = Modifier.padding(innerPadding))
                        else -> Box(modifier = Modifier.padding(innerPadding)) {
                            Text(text = "masih kosong")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
    var selectedFilterIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    Column(modifier = modifier) {
        // filter
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            listOf("Semua", "Belum dibaca", "Grup").mapIndexed { index, item ->
                FilterChip(
                    onClick = {selectedFilterIndex = index},
                    label = { Text(text = item) },
                    selected = selectedFilterIndex == index,
                )
            }
        }

        // list chat
        ChatListItem(
            name = "Rizal Dwi Anggoro",
            lastMessage = "hello world",
            time = "12.12",
            isRead = true,
            lastUser = LastUser.ME,
            onClick = {
                Toast.makeText(context, "Rizal diclick!", Toast.LENGTH_SHORT).show()
            }
        )
        ChatListItem(
            name = "Rizal Dwi Anggoro",
            lastMessage = "hello world",
            time = "12.12",
            isRead = true,
            lastUser = LastUser.ME
        )
        ChatListItem(
            name = "Rizal Dwi Anggoro",
            lastMessage = "hello world",
            time = "12.12",
            isRead = false,
            lastUser = LastUser.OTHER
        )
    }
}

enum class LastUser {
    ME,
    OTHER
}

@Composable
fun ChatListItem(
    name: String,
    lastMessage: String,
    lastUser: LastUser,
    time: String,
    isRead: Boolean,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .clickable {
                if (onClick != null)
                    onClick()
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // photo profile
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Icon(
                Lucide.User,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(18.dp)
            )
        }

        // content
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // name, last message
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                Text(text = "${if (lastUser == LastUser.ME) "You: " else ""}${lastMessage}", style = MaterialTheme.typography.bodyMedium)
            }

            // time
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = time, style = MaterialTheme.typography.labelMedium)
                if (!isRead)
                    Badge {
                        Text(text = "6", style = MaterialTheme.typography.labelSmall)
                    }
            }
        }
    }
}

@Composable
fun StatusScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Ini halaman pembaruan")
    }
}