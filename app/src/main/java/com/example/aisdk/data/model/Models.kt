package com.example.aisdk.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class AppointmentDetails(
    val title: String,
    val date: String,
    val startTime: String? = null,
    val endTime: String? = null,
    val location: String? = null,
    val attendees: List<String>? = null
)

@Serializable
data class WeatherData(
    val city: String,
    val temperature: Int,
    val weatherCode: Int,
    val humidity: Int,
    val condition: String? = null
)

@Serializable
data class CommentAuthor(
    val name: String,
    val initials: String,
    val avatarSrc: String = ""
)

@Serializable
data class CommentMessage(
    val id: Int,
    val author: CommentAuthor,
    val timestamp: String,
    val content: String,
    val likes: Int = 0,
    val dislikes: Int = 0
)

@Serializable
data class SummaryCardData(
    val headline: String,
    val context: String,
    val discussionPoints: String,
    val takeaways: String
)

@Serializable
data class SupportTicket(
    val id: Int,
    val text: String,
    val category: String = "uncategorized",
    val priority: String = "medium",
    val suggestedReply: String = ""
)

@Serializable
data class EssayExtractionResult(
    val essayTitle: String,
    val coreThesis: String,
    val founderModeTraits: List<String>,
    val managerModeTraits: List<String>,
    val keyTakeaways: List<String>,
    val notableQuotes: List<String>
)

enum class ChatSender {
    USER,
    ASSISTANT,
    SYSTEM
}

data class ChatMessage(
    val id: String,
    val sender: ChatSender,
    val text: String,
    val weatherData: WeatherData? = null,
    val isGenerating: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

// Gemini API REST Request & Response models
@Serializable
data class GenerateContentRequest(
    val contents: List<GeminiContent>,
    val generationConfig: GeminiGenerationConfig? = null,
    val systemInstruction: GeminiContent? = null,
    val tools: List<JsonObject>? = null
)

@Serializable
data class GeminiContent(
    val role: String? = null,
    val parts: List<GeminiPart>
)

@Serializable
data class GeminiPart(
    val text: String? = null
)

@Serializable
data class GeminiGenerationConfig(
    val responseMimeType: String? = null,
    val temperature: Float? = null,
    val topP: Float? = null,
    val topK: Int? = null
)

@Serializable
data class GeminiResponse(
    val candidates: List<GeminiCandidate>? = null
)

@Serializable
data class GeminiCandidate(
    val content: GeminiContent? = null
)
