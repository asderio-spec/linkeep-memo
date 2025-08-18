package com.linkeep.memo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.linkeep.memo.ui.viewmodels.MemoViewModel

@Composable
fun DetailScreen(memoId: Long, onBack: () -> Unit, vm: MemoViewModel = hiltViewModel()) {
    val memoList by vm.memos.collectAsState()
    val memo = memoList.find { it.id == memoId }
    var title by remember { mutableStateOf(memo?.title ?: "") }
    var content by remember { mutableStateOf(memo?.content ?: "") }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("메모 상세") },
            navigationIcon = { TextButton(onClick = onBack) { Text("뒤로") } }
        )
    }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("제목") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("내용") }, modifier = Modifier.fillMaxWidth(), minLines = 6)
            Spacer(Modifier.height(12.dp))
            Button(onClick = {
                memo?.let { m ->
                    vm.updateMemo(m.copy(title = title, content = content))
                    onBack()
                }
            }) { Text("저장") }
        }
    }
}
