@file:OptIn(ExperimentalMaterial3Api::class)

package com.linkeep.memo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.linkeep.memo.data.model.Memo
import com.linkeep.memo.ui.components.AddMemoDialog
import com.linkeep.memo.ui.viewmodels.MemoViewModel
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MemoViewModel = hiltViewModel()
) {
    val memos by viewModel.filteredMemos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var showSearchBar by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (showSearchBar) {
                SearchBar(
                    query = viewModel.searchQuery.collectAsState().value,
                    onQueryChange = viewModel::setSearchQuery,
                    onSearch = { },
                    active = true,
                    onActiveChange = { },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    },
                    placeholder = { Text("검색어를 입력하세요") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 검색 제안이 필요한 경우 여기에 추가
                }
            } else {
                TopAppBar(
                    title = { Text("Linkeep Memo") },
                    actions = {
                        IconButton(onClick = { showSearchBar = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                        var cardView by remember { mutableStateOf(true) }
                        IconButton(onClick = { cardView = !cardView }) {
                            Icon(
                                if (cardView) Icons.Default.ViewList else Icons.Default.GridView,
                                contentDescription = "Toggle View"
                            )
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Memo")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Text(
                            text = "카테고리",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CategoryRow(
                            categories = categories,
                            selected = selectedCategory,
                            onSelect = viewModel::setSelectedCategory
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    items(memos) { memo ->
                        MemoItem(
                            memo = memo,
                            onEdit = { updated -> viewModel.updateMemo(updated) }
                        )
                    }
                }
            }
        }

        if (showAddDialog) {
            AddMemoDialog(
                onDismiss = { showAddDialog = false },
                onSave = { title, content, link, category ->
                    viewModel.generateMemoContent(title, content, link, category)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
private fun CategoryRow(
    categories: List<String>,
    selected: String?,
    onSelect: (String?) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            FilterChip(
                selected = selected == null,
                onClick = { onSelect(null) },
                label = { Text("전체") }
            )
        }
        items(categories) { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onSelect(if (selected == category) null else category) },
                label = { Text(category) }
            )
        }
    }
}

@Composable
fun MemoItem(memo: Memo, onEdit: (Memo) -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (!memo.thumbnailUrl.isNullOrBlank()) {
                AsyncImage(
                    model = memo.thumbnailUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(
                text = memo.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = memo.content,
                style = MaterialTheme.typography.bodyMedium
            )
            if (memo.link != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = memo.link,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = memo.category,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = {
                    // 간단한 인라인 수정(제목 뒤에 * 추가 예시). 실제로는 별도 Dialog를 권장
                    onEdit(memo.copy(title = memo.title + " *"))
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("수정")
                }
            }
        }
    }
} 