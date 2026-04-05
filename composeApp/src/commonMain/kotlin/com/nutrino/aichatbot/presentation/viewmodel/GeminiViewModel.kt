package com.nutrino.aichatbot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrino.aichatbot.domain.common.ResultState
import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionRequest
import com.nutrino.aichatbot.domain.useCases.AskQuestionWithPromptUseCase
import com.nutrino.aichatbot.presentation.states.AskQuestionsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel that manages the Ask Question screen state.
 *
 * The ViewModel receives a domain request, delegates the operation to the use case, and
 * converts the domain result into a UI-friendly state stream.
 */
class GeminiViewModel(
    private val askQuestionWithPromptUseCase: AskQuestionWithPromptUseCase
) : ViewModel() {
    /**
     * Exposes the current UI state for the Ask Question screen.
     */
    private val _askQuestionUIState = MutableStateFlow<AskQuestionsUIState>(AskQuestionsUIState.Idle)

    /**
     * Public read-only view of the Ask Question UI state.
     */
    val askQuestionUIState = _askQuestionUIState.asStateFlow()

    /**
     * Sends a prompt request to the use case and updates the UI state from the result stream.
     *
     * @param request The domain request constructed from the user's message input.
     */
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