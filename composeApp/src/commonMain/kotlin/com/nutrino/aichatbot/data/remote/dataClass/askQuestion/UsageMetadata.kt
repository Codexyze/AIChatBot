package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsageMetadata(
    @SerialName("candidatesTokenCount")
    val candidatesTokenCount: Int?,
    @SerialName("promptTokenCount")
    val promptTokenCount: Int?,
    @SerialName("promptTokensDetails")
    val promptTokensDetails: List<PromptTokensDetail?>?,
    @SerialName("thoughtsTokenCount")
    val thoughtsTokenCount: Int?,
    @SerialName("totalTokenCount")
    val totalTokenCount: Int?
)