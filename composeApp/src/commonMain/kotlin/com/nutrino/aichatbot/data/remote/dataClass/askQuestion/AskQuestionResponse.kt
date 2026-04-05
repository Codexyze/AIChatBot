package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable Gemini response DTO used only by the remote data layer.
 *
 * This type mirrors the API response exactly so Ktor can deserialize the payload before the
 * repository maps it into the domain model.
 */
@Serializable
data class AskQuestionResponse(
    @SerialName("candidates")
    val candidates: List<Candidate>?,
    @SerialName("modelVersion")
    val modelVersion: String?,
    @SerialName("responseId")
    val responseId: String?,
    @SerialName("usageMetadata")
    val usageMetadata: UsageMetadata?
)