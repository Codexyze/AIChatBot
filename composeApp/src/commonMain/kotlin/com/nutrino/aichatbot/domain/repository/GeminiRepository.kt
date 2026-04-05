package com.nutrino.aichatbot.domain.repository

import com.nutrino.aichatbot.domain.common.ResultState
import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionRequest
import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionResponse
import kotlinx.coroutines.flow.Flow

/**
 * Defines the domain contract for asking Gemini a question.
 *
 * Implementations are responsible for taking a domain request, performing the remote call,
 * and emitting a stream of loading, success, or error states.
 */
interface GeminiRepository {

    /**
     * Sends a question to the remote Gemini API and returns the result as a flow.
     *
     * @param request The domain request containing the user's prompt content.
     * @return A flow that emits loading first and then either a successful response or an error.
     */
    suspend fun askQuestionWithPrompt(request: AskQuestionRequest): Flow<ResultState<AskQuestionResponse>>
}

