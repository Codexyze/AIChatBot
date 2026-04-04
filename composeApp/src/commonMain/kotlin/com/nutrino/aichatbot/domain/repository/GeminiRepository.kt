package com.nutrino.aichatbot.domain.repository

import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionResponse
import com.nutrino.aichatbot.domain.common.ResultState
import kotlinx.coroutines.flow.Flow

interface GeminiRepository {

    suspend fun askQuestionWithPrompt(question: String): Flow<ResultState<AskQuestionResponse>>
}

