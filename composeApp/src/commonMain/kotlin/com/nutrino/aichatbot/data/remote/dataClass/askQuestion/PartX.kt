package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable text part used in the Gemini response DTO.
 */
@Serializable
data class PartX(
    @SerialName("text")
    val text: String?,
    @SerialName("thoughtSignature")
    val thoughtSignature: String?
)