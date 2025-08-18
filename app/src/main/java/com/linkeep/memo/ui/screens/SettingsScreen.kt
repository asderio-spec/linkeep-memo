package com.linkeep.memo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.linkeep.memo.data.AIProvider
import com.linkeep.memo.data.ThemeMode
import com.linkeep.memo.data.ViewMode
import com.linkeep.memo.ui.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(vm: SettingsViewModel = hiltViewModel()) {
    val theme by vm.themeMode.collectAsState()
    val viewMode by vm.viewMode.collectAsState()
    val provider by vm.aiProvider.collectAsState()
    val apiKey by vm.aiApiKey.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("설정") }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("테마", style = MaterialTheme.typography.titleMedium)
            AssistChip(onClick = { vm.setThemeMode(ThemeMode.SYSTEM) }, label = { Text("시스템") }, enabled = theme != ThemeMode.SYSTEM)
            AssistChip(onClick = { vm.setThemeMode(ThemeMode.LIGHT) }, label = { Text("라이트") }, enabled = theme != ThemeMode.LIGHT)
            AssistChip(onClick = { vm.setThemeMode(ThemeMode.DARK) }, label = { Text("다크") }, enabled = theme != ThemeMode.DARK)

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Text("보기 방식", style = MaterialTheme.typography.titleMedium)
            AssistChip(onClick = { vm.setViewMode(ViewMode.CARD) }, label = { Text("카드") }, enabled = viewMode != ViewMode.CARD)
            AssistChip(onClick = { vm.setViewMode(ViewMode.LIST) }, label = { Text("리스트") }, enabled = viewMode != ViewMode.LIST)

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Text("AI 제공자", style = MaterialTheme.typography.titleMedium)
            AssistChip(onClick = { vm.setAIProvider(AIProvider.LOCAL) }, label = { Text("로컬") }, enabled = provider != AIProvider.LOCAL)
            AssistChip(onClick = { vm.setAIProvider(AIProvider.OPENAI) }, label = { Text("OpenAI") }, enabled = provider != AIProvider.OPENAI)
            AssistChip(onClick = { vm.setAIProvider(AIProvider.GEMINI) }, label = { Text("Gemini") }, enabled = provider != AIProvider.GEMINI)
            AssistChip(onClick = { vm.setAIProvider(AIProvider.CLAUDE) }, label = { Text("Claude") }, enabled = provider != AIProvider.CLAUDE)

            OutlinedTextField(
                value = apiKey,
                onValueChange = { vm.setAIApiKey(it) },
                label = { Text("API 키") },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
