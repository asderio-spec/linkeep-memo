package com.linkeep.memo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linkeep.memo.ui.screens.MainScreen
import com.linkeep.memo.ui.theme.MemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoTheme {
                val navController = rememberNavController()
                var selectedTab by remember { mutableStateOf("home") }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = selectedTab == "home",
                                onClick = { selectedTab = "home"; navController.navigate("home") },
                                icon = { Icon(Icons.Default.Home, contentDescription = "홈") },
                                label = { Text("홈") }
                            )
                            NavigationBarItem(
                                selected = selectedTab == "category",
                                onClick = { selectedTab = "category"; navController.navigate("category") },
                                icon = { Icon(Icons.Default.ViewModule, contentDescription = "카테고리") },
                                label = { Text("카테고리") }
                            )
                            NavigationBarItem(
                                selected = selectedTab == "date",
                                onClick = { selectedTab = "date"; navController.navigate("date") },
                                icon = { Icon(Icons.Default.Today, contentDescription = "날짜") },
                                label = { Text("날짜") }
                            )
                            NavigationBarItem(
                                selected = selectedTab == "settings",
                                onClick = { selectedTab = "settings"; navController.navigate("settings") },
                                icon = { Icon(Icons.Default.Settings, contentDescription = "설정") },
                                label = { Text("설정") }
                            )
                        }
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") { MainScreen(onOpen = { id -> navController.navigate("detail/$id") }) }
                            composable("category") { PlaceholderScreen("카테고리") }
                            composable("date") { PlaceholderScreen("날짜") }
                            composable("settings") { com.linkeep.memo.ui.screens.SettingsScreen() }
                            composable("detail/{id}") { backStackEntry ->
                                val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: 0L
                                com.linkeep.memo.ui.screens.DetailScreen(memoId = id, onBack = { navController.popBackStack() })
                            }
                        }
                    }
                }
            }
        }
    }
} 

@Composable
private fun PlaceholderScreen(title: String) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
    }
}