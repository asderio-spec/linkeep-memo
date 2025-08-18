package com.linkeep.memo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.linkeep.memo.ui.viewmodels.MemoViewModel

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun CategoryScreen(onOpen: (Long) -> Unit = {}, vm: MemoViewModel = hiltViewModel()) {
    val allMemos by vm.memos.collectAsState()
    val categories by vm.categories.collectAsState()
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val recentCategories = remember(categories) { categories.takeLast(10).reversed() }
    val suggestions = remember(query.text, categories) {
        if (query.text.isBlank()) emptyList() else categories.filter { it.contains(query.text, true) }.take(10)
    }
    val filtered = remember(allMemos, selectedCategory, query.text) {
        allMemos.filter { memo ->
            val byCat = selectedCategory?.let { memo.category == it } ?: true
            val byQuery = if (query.text.isBlank()) true else memo.category.contains(query.text, true)
            byCat && byQuery
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("카테고리") }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                placeholder = { Text("카테고리 검색") },
                modifier = Modifier.fillMaxWidth()
            )
            val flowSpacing = 8.dp
            androidx.compose.foundation.layout.FlowRow(
                horizontalArrangement = Arrangement.spacedBy(flowSpacing),
                verticalArrangement = Arrangement.spacedBy(flowSpacing)
            ) {
                if (suggestions.isNotEmpty()) {
                    suggestions.forEach { s ->
                        AssistChip(onClick = { selectedCategory = s; query = TextFieldValue(s) }, label = { Text(s) })
                    }
                }
            }
            Text("최근 카테고리", style = MaterialTheme.typography.titleSmall)
            androidx.compose.foundation.layout.FlowRow(
                horizontalArrangement = Arrangement.spacedBy(flowSpacing),
                verticalArrangement = Arrangement.spacedBy(flowSpacing)
            ) {
                recentCategories.forEach { c ->
                    FilterChip(selected = selectedCategory == c, onClick = { selectedCategory = if (selectedCategory == c) null else c }, label = { Text(c) })
                }
            }
            Divider()
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filtered) { memo ->
                    MemoItemList(memo = memo, onClick = { onOpen(memo.id) })
                }
            }
        }
    }
}
