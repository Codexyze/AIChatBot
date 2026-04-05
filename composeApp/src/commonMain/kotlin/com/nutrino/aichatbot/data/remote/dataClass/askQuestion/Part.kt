package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable text part used in the Gemini request DTO.
 */
@Serializable
data class Part(
    @SerialName("text")
    val text: String?
)