package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable breakdown of prompt token usage for a specific modality.
 */
@Serializable
data class PromptTokensDetail(
    @SerialName("modality")
    val modality: String?,
    @SerialName("tokenCount")
    val tokenCount: Int?
)