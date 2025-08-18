package com.linkeep.memo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.linkeep.memo.ui.viewmodels.MemoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateScreen(onOpen: (Long) -> Unit = {}, vm: MemoViewModel = hiltViewModel()) {
    val allMemos by vm.memos.collectAsState()
    var preset by remember { mutableStateOf("1d") }
    var showPicker by remember { mutableStateOf(false) }
    var fromTs by remember { mutableStateOf<Long?>(null) }
    var toTs by remember { mutableStateOf<Long?>(null) }

    fun now() = System.currentTimeMillis()
    fun days(n: Int) = n * 24L * 60L * 60L * 1000L

    val range = remember(preset, fromTs, toTs) {
        when (preset) {
            "1d" -> now() - days(1) to now()
            "3d" -> now() - days(3) to now()
            "1w" -> now() - days(7) to now()
            "2w" -> now() - days(14) to now()
            else -> (fromTs ?: now() - days(7)) to (toTs ?: now())
        }
    }

    val filtered = remember(allMemos, range) {
        allMemos.filter { it.createdAt in range.first..range.second }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("날짜") }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = preset == "1d", onClick = { preset = "1d" }, label = { Text("최근 1일") })
                FilterChip(selected = preset == "3d", onClick = { preset = "3d" }, label = { Text("최근 3일") })
                FilterChip(selected = preset == "1w", onClick = { preset = "1w" }, label = { Text("최근 1주일") })
                FilterChip(selected = preset == "2w", onClick = { preset = "2w" }, label = { Text("최근 2주일") })
                AssistChip(onClick = { preset = "custom"; showPicker = true }, label = { Text("상세 선택") })
            }
            if (showPicker) {
                Text(text = "상세 기간 선택 팝업 (차기 단계에서 DatePicker 연결)")
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
