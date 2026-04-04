package com.nutrino.aichatbot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrino.aichatbot.data.remote.dataClass.askQuestion.AskQuestionRequest
import com.nutrino.aichatbot.domain.common.ResultState
import com.nutrino.aichatbot.domain.useCases.AskQuestionWithPromptUseCase
import com.nutrino.aichatbot.presentation.states.AskQuestionsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GeminiViewModel(
    private val askQuestionWithPromptUseCase: AskQuestionWithPromptUseCase
) : ViewModel() {
    private val _askQuestionUIState = MutableStateFlow<AskQuestionsUIState>(AskQuestionsUIState.Idle)
    val askQuestionUIState = _askQuestionUIState.asStateFlow()

    fun askQuestionUsingPrompt(request: AskQuestionRequest){
        viewModelScope.launch {
            askQuestionWithPromptUseCase.invoke(request = request).collect { result ->
                when (result) {
                    is ResultState.isLoading -> {
                        _askQuestionUIState.value = AskQuestionsUIState.isLoading
                    }
                    is ResultState.Success -> {
                        _askQuestionUIState.value = AskQuestionsUIState.Success(result.data)
                    }
                    is ResultState.Error -> {
                        _askQuestionUIState.value = AskQuestionsUIState.Error(result.message)
                    }
                }

            }
        }
    }


}