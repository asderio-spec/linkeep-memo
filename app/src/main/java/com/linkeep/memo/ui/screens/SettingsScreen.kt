package com.linkeep.memo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.linkeep.memo.data.AIProvider
import com.linkeep.memo.data.ThemeMode
import com.linkeep.memo.data.ViewMode
import com.linkeep.memo.ui.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(vm: SettingsViewModel = hiltViewModel()) {
    val theme by vm.themeMode.collectAsState()
    val viewMode by vm.viewMode.collectAsState()
    val provider by vm.aiProvider.collectAsState()
    val apiKey by vm.aiApiKey.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(id = com.linkeep.memo.R.string.title_settings)) }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Theme Section
            Text(stringResource(id = com.linkeep.memo.R.string.label_theme), style = MaterialTheme.typography.titleMedium)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(onClick = { vm.setThemeMode(ThemeMode.SYSTEM) }, label = { Text(stringResource(id = com.linkeep.memo.R.string.theme_system)) }, enabled = theme != ThemeMode.SYSTEM)
                AssistChip(onClick = { vm.setThemeMode(ThemeMode.LIGHT) }, label = { Text(stringResource(id = com.linkeep.memo.R.string.theme_light)) }, enabled = theme != ThemeMode.LIGHT)
                AssistChip(onClick = { vm.setThemeMode(ThemeMode.DARK) }, label = { Text(stringResource(id = com.linkeep.memo.R.string.theme_dark)) }, enabled = theme != ThemeMode.DARK)
            }

            Divider()

            // View Mode Section
            Text(stringResource(id = com.linkeep.memo.R.string.label_view_mode), style = MaterialTheme.typography.titleMedium)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(onClick = { vm.setViewMode(ViewMode.CARD) }, label = { Text(stringResource(id = com.linkeep.memo.R.string.view_card)) }, enabled = viewMode != ViewMode.CARD)
                AssistChip(onClick = { vm.setViewMode(ViewMode.LIST) }, label = { Text(stringResource(id = com.linkeep.memo.R.string.view_list)) }, enabled = viewMode != ViewMode.LIST)
            }

            Divider()

            // AI Provider Section
            Text(stringResource(id = com.linkeep.memo.R.string.label_ai_provider), style = MaterialTheme.typography.titleMedium)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(onClick = { vm.setAIProvider(AIProvider.LOCAL) }, label = { Text(stringResource(id = com.linkeep.memo.R.string.ai_local)) }, enabled = provider != AIProvider.LOCAL)
                AssistChip(onClick = { vm.setAIProvider(AIProvider.OPENAI) }, label = { Text(stringResource(id = com.linkeep.memo.R.string.ai_openai)) }, enabled = provider != AIProvider.OPENAI)
                AssistChip(onClick = { vm.setAIProvider(AIProvider.GEMINI) }, label = { Text(stringResource(id = com.linkeep.memo.R.string.ai_gemini)) }, enabled = provider != AIProvider.GEMINI)
                AssistChip(onClick = { vm.setAIProvider(AIProvider.CLAUDE) }, label = { Text(stringResource(id = com.linkeep.memo.R.string.ai_claude)) }, enabled = provider != AIProvider.CLAUDE)
            }
            OutlinedTextField(
                value = apiKey,
                onValueChange = { vm.setAIApiKey(it) },
                label = { Text(stringResource(id = com.linkeep.memo.R.string.label_api_key)) },
                modifier = Modifier.fillMaxWidth()
            )

            Divider()

            // Language Section
            Text(stringResource(id = com.linkeep.memo.R.string.label_language), style = MaterialTheme.typography.titleMedium)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(onClick = { vm.setLanguage("ko") }, label = { Text(stringResource(id = com.linkeep.memo.R.string.lang_ko)) })
                AssistChip(onClick = { vm.setLanguage("en") }, label = { Text(stringResource(id = com.linkeep.memo.R.string.lang_en)) })
            }
        }
    }
}
