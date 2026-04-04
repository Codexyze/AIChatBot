package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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