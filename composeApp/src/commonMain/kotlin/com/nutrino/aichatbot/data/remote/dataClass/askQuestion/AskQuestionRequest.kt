package com.nutrino.aichatbot.data.remote.dataClass.askQuestion


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable Gemini request DTO used only by the remote data layer.
 *
 * The repository maps the domain request into this structure right before sending the network
 * call so the rest of the app never depends on serialization annotations.
 */
@Serializable
data class AskQuestionRequest(
    @SerialName("contents")
    val contents: List<Content?>?
)