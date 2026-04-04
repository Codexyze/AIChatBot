package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Candidate(
    @SerialName("content")
    val content: ContentX?,
    @SerialName("finishReason")
    val finishReason: String?,
    @SerialName("index")
    val index: Int?
)