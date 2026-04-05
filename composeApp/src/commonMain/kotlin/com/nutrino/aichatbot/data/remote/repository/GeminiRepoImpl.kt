package com.nutrino.aichatbot.data.remote.repository

import com.nutrino.aichatbot.Constants.GeminiConstants
import com.nutrino.aichatbot.domain.common.ResultState
import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionRequest
import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionResponse
import com.nutrino.aichatbot.domain.repository.GeminiRepository
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionResponse as RemoteAskQuestionResponse
import com.nutrino.aichatbot.data.remote.mapper.askQuestion.toRemoteModel
import com.nutrino.aichatbot.data.remote.mapper.askQuestion.toDomainModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repository implementation that sends Ask Question requests to the Gemini API.
 *
 * The class converts domain models into remote DTOs, performs the HTTP call, and converts the
 * API response back into domain models before emitting it to the caller.
 */
class GeminiRepoImpl(
    private val httpClient: HttpClient
): GeminiRepository {
    /**
     * Sends the request to Gemini and emits loading, success, or error states.
     *
     * @param request The domain request built from the user prompt.
     * @return A flow that starts with loading and then emits the mapped domain response or an error.
     */
    override suspend fun askQuestionWithPrompt(request: AskQuestionRequest): Flow<ResultState<AskQuestionResponse>> = flow{
        emit(ResultState.isLoading)
        try {
            val apiResponse = httpClient.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent") {
                header("x-goog-api-key", GeminiConstants.GEMINI_API_KEY)
                header("Content-Type", ContentType.Application.Json)
                setBody(request.toRemoteModel())
            }.body<RemoteAskQuestionResponse>()
            emit(ResultState.Success(apiResponse.toDomainModel()))
        }catch (e: Exception){
            emit(ResultState.Error(e.message.toString()))

        }

    }
}