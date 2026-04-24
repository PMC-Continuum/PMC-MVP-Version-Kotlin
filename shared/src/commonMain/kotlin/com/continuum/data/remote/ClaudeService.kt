package com.continuum.data.remote

import com.continuum.config.AppConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class ClaudeService(private val client: HttpClient) {

    @Serializable
    private data class ClaudeMessage(val role: String, val content: String)

    @Serializable
    private data class ClaudeRequest(
        val model: String = AppConfig.ANTHROPIC_MODEL,
        val max_tokens: Int = 1024,
        val system: String,
        val messages: List<ClaudeMessage>
    )

    @Serializable
    private data class ContentBlock(val type: String, val text: String? = null)

    @Serializable
    private data class ClaudeResponse(val content: List<ContentBlock>)

    suspend fun complete(systemPrompt: String, userMessage: String): String {
        val response: ClaudeResponse = client
            .post("${AppConfig.ANTHROPIC_BASE_URL}/messages") {
                header("x-api-key", AppConfig.ANTHROPIC_API_KEY)
                header("anthropic-version", "2023-06-01")
                contentType(ContentType.Application.Json)
                setBody(
                    ClaudeRequest(
                        system = systemPrompt,
                        messages = listOf(ClaudeMessage("user", userMessage))
                    )
                )
            }.body()
        return response.content
            .firstOrNull { it.type == "text" }?.text
            ?: error("Empty response from Claude API")
    }
}
