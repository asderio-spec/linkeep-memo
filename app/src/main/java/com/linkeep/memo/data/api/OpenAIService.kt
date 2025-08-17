package com.linkeep.memo.data.api

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAIService {
    @POST("v1/chat/completions")
    suspend fun generateContent(
        @Header("Authorization") apiKey: String,
        @Body request: OpenAIRequest
    ): OpenAIResponse
}

data class OpenAIRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<Message>,
    val temperature: Double = 0.7
)

data class Message(
    val role: String,
    val content: String
)

data class OpenAIResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
) 