package com.nutrino.aichatbot.data.remote.repository

import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionResponse
import com.nutrino.aichatbot.domain.common.ResultState
import com.nutrino.aichatbot.domain.repository.GeminiRepository
import kotlinx.coroutines.flow.Flow

class GeminiRepoImpl: GeminiRepository {
    override suspend fun askQuestionWithPrompt(question: String): Flow<ResultState<AskQuestionResponse>> {
        TODO("Not yet implemented")
    }
}