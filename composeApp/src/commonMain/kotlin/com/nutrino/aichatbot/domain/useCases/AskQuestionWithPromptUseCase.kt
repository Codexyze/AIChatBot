package com.nutrino.aichatbot.domain.useCases

import com.nutrino.aichatbot.domain.common.ResultState
import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionRequest
import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionResponse
import com.nutrino.aichatbot.domain.repository.GeminiRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case that coordinates a single ask-question operation.
 *
 * The class keeps the presentation layer decoupled from the repository implementation by
 * exposing one small domain function that simply delegates to the repository contract.
 */
class AskQuestionWithPromptUseCase(private val geminiRepository: GeminiRepository) {

    /**
     * Executes the ask-question flow using the provided domain request.
     *
     * @param request The request object built from the user's input.
     * @return A flow of `ResultState` values describing loading, success, or error states.
     */
    suspend operator fun invoke(request: AskQuestionRequest): Flow<ResultState<AskQuestionResponse>>{
        return geminiRepository.askQuestionWithPrompt(request = request)
    }
}