package com.nutrino.aichatbot.presentation.states

import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionResponse

sealed class AskQuestionsUIState{
    object Idle: AskQuestionsUIState()
    object isLoading: AskQuestionsUIState()
    data class Success(val data: AskQuestionResponse): AskQuestionsUIState()
    data class Error(val message: String): AskQuestionsUIState()
}