package com.nutrino.aichatbot.data.remote.repository

import com.nutrino.aichatbot.Constants.GeminiConstants
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionRequest
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionResponse
import com.nutrino.aichatbot.domain.common.ResultState
import com.nutrino.aichatbot.domain.repository.GeminiRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GeminiRepoImpl(
    private val httpClient: HttpClient
): GeminiRepository {
    override suspend fun askQuestionWithPrompt(request: AskQuestionRequest): Flow<ResultState<AskQuestionResponse>> = flow{
        emit(ResultState.isLoading)
        try {
            val apiResponse = httpClient.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent") {
                header("x-goog-api-key", GeminiConstants.GEMINI_API_KEY)
                header("Content-Type", ContentType.Application.Json)
                setBody(request)
            }.body<AskQuestionResponse>()
            emit(ResultState.Success(apiResponse))
        }catch (e: Exception){
            emit(ResultState.Error(e.message.toString()))

        }

    }
}