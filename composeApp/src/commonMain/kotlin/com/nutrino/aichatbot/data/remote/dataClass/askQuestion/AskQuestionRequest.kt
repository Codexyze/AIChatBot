package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AskQuestionRequest(
    @SerialName("contents")
    val contents: List<Content?>?
)