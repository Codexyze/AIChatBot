package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable generated content block returned inside a Gemini response candidate.
 */
@Serializable
data class ContentX(
    @SerialName("parts")
    val parts: List<PartX>?,
    @SerialName("role")
    val role: String?
)