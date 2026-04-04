package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PromptTokensDetail(
    @SerialName("modality")
    val modality: String?,
    @SerialName("tokenCount")
    val tokenCount: Int?
)