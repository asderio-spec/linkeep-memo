package com.linkeep.memo.data.service

import com.linkeep.memo.data.api.OpenAIService
import com.linkeep.memo.data.api.OpenAIRequest
import com.linkeep.memo.data.api.Message
import com.linkeep.memo.BuildConfig
import com.linkeep.memo.data.AIProvider
import com.linkeep.memo.data.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LLMService @Inject constructor(
    private val openAIService: OpenAIService,
    private val settingsRepository: SettingsRepository
) {
    suspend fun generateMemoContent(
        title: String? = null,
        content: String? = null,
        link: String? = null,
        category: String? = null
    ): Triple<String, String, String> {
        // 설정 기반 제공자/키 선택
        val provider = settingsRepository.aiProviderFlow.first()
        val settingsKey = settingsRepository.aiApiKeyFlow.first()
        val envKey = BuildConfig.OPENAI_API_KEY
        val apiKey = if (settingsKey.isNotBlank()) settingsKey else envKey

        if (provider != AIProvider.OPENAI || apiKey.isBlank()) return generateFallback(title, content, link, category)

        return try {
            val prompt = buildString {
                append("다음 정보를 바탕으로 메모를 작성해주세요:\n")
                if (link != null) append("링크: $link\n")
                if (category != null) append("카테고리: $category\n")
                append("\n제목, 내용, 카테고리를 생성해주세요. 각각 한 줄로 작성해주세요.")
            }

            val request = OpenAIRequest(
                messages = listOf(
                    Message("system", "당신은 메모 작성 도우미입니다. 주어진 정보를 바탕으로 간단하고 명확한 메모를 작성해주세요."),
                    Message("user", prompt)
                )
            )

            val response = withContext(Dispatchers.IO) {
                openAIService.generateContent(
                    apiKey = "Bearer $apiKey",
                    request = request
                )
            }

            val generatedContent = response.choices.firstOrNull()?.message?.content ?: ""
            val lines = generatedContent.split("\n").filter { it.isNotBlank() }

            Triple(
                title ?: lines.getOrNull(0) ?: (link ?: "새 메모"),
                content ?: lines.getOrNull(1) ?: "요약이 생성되지 않았습니다.",
                category ?: lines.getOrNull(2) ?: (category ?: "기타")
            )
        } catch (e: Exception) {
            generateFallback(title, content, link, category)
        }
    }

    private fun generateFallback(
        title: String?,
        content: String?,
        link: String?,
        category: String?
    ): Triple<String, String, String> {
        val fallbackTitle = title ?: (link ?: "임시 메모")
        val fallbackContent = content ?: buildString {
            append("AI 요약 비활성화 상태입니다.\n")
            link?.let { append("링크: $it") }
        }
        val fallbackCategory = category ?: "기타"
        return Triple(fallbackTitle, fallbackContent, fallbackCategory)
    }
} 