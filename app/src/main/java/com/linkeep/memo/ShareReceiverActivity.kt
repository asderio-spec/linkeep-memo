package com.linkeep.memo

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material3.*
 
import kotlinx.coroutines.delay
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.linkeep.memo.data.repository.MemoRepository
import com.linkeep.memo.data.model.Memo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

@AndroidEntryPoint
class ShareReceiverActivity : ComponentActivity() {

    @Inject lateinit var memoRepository: MemoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (intent?.action) {
            Intent.ACTION_SEND, Intent.ACTION_SEND_MULTIPLE -> {
                setContent {
                    var message by remember { mutableStateOf("저장 중...") }
                    Surface { com.linkeep.memo.ui.screens.ShareSavingSheet(message = message) { /* dismiss disabled */ } }
                    LaunchedEffect(Unit) {
                        handleSendWithFeedback(intent) { msg -> message = msg }
                    }
                }
            }
            else -> finish()
        }
    }

    private suspend fun handleSendWithFeedback(intent: Intent, onMessage: (String) -> Unit) {
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        val sharedStream: Uri? = intent.getParcelableExtra(Intent.EXTRA_STREAM)

        val link = sharedText?.let { extractFirstUrl(it) }
        val title = sharedText?.take(80) ?: "공유 항목"
        val category = "공유"

        lifecycleScope.launch(Dispatchers.IO) {
            val memo = Memo(
                title = title,
                content = sharedText ?: "",
                link = link,
                category = category,
                thumbnailUrl = link?.let { fetchOpenGraphImage(it) }
            )
            memoRepository.insertMemo(memo)
            launch(Dispatchers.Main) {
                onMessage("저장 완료")
                delay(900)
                finish()
            }
        }
    }

    private fun extractFirstUrl(text: String): String? {
        val regex = Regex("https?://[\\w./?=&%#-]+", RegexOption.IGNORE_CASE)
        return regex.find(text)?.value
    }

    private fun fetchOpenGraphImage(url: String): String? {
        // 간단한 썸네일 추출: og:image를 직접 파싱하는 대신 기본 파비콘 시도
        // 실제 구현은 서버 API 또는 HTML 파서 사용 권장
        return try {
            val uri = Uri.parse(url)
            "${uri.scheme}://${uri.host}/favicon.ico"
        } catch (e: Exception) {
            null
        }
    }
}
