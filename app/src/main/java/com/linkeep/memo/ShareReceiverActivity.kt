package com.linkeep.memo

import androidx.activity.ComponentActivity
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
            Intent.ACTION_SEND -> handleSend(intent)
            Intent.ACTION_SEND_MULTIPLE -> handleSend(intent)
            else -> finish()
        }
    }

    private fun handleSend(intent: Intent) {
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
                Toast.makeText(this@ShareReceiverActivity, "저장 완료", Toast.LENGTH_SHORT).show()
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
