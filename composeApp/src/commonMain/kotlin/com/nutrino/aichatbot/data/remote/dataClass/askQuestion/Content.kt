package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable request content block used by the remote Gemini DTO.
 */
@Serializable
data class Content(
    @SerialName("parts")
    val parts: List<Part?>?
)