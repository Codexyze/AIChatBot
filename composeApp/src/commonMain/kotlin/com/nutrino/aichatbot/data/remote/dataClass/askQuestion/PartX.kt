package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartX(
    @SerialName("text")
    val text: String?,
    @SerialName("thoughtSignature")
    val thoughtSignature: String?
)