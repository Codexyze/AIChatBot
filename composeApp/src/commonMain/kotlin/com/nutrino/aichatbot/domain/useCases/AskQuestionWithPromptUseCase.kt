package com.nutrino.aichatbot.domain.useCases

import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionRequest
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionResponse
import com.nutrino.aichatbot.domain.common.ResultState
import com.nutrino.aichatbot.domain.repository.GeminiRepository
import kotlinx.coroutines.flow.Flow

class AskQuestionWithPromptUseCase(private val geminiRepository: GeminiRepository) {
    suspend operator fun invoke(request: AskQuestionRequest): Flow<ResultState<AskQuestionResponse>>{
        return geminiRepository.askQuestionWithPrompt(request = request)
    }
}