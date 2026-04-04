package com.nutrino.aichatbot.domain.repository

import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionRequest
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionResponse
import com.nutrino.aichatbot.domain.common.ResultState
import kotlinx.coroutines.flow.Flow

interface GeminiRepository {

    suspend fun askQuestionWithPrompt(request: AskQuestionRequest): Flow<ResultState<AskQuestionResponse>>
}

