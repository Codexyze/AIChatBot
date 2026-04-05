package com.nutrino.aichatbot.presentation.states

import com.nutrino.aichatbot.domain.model.askQuestion.AskQuestionResponse

/**
 * Represents the state of the Ask Question screen.
 *
 * The screen uses this sealed class to render the initial idle state, the loading indicator,
 * the successful response, or an error message.
 */
sealed class AskQuestionsUIState{
    /**
     * Indicates that the screen has not started a request yet or has returned to its default state.
     */
    object Idle: AskQuestionsUIState()

    /**
     * Indicates that a network request is currently in progress.
     */
    object isLoading: AskQuestionsUIState()

    /**
     * Represents a successful response ready to be displayed in the UI.
     *
     * @property data The domain response returned by the repository.
     */
    data class Success(val data: AskQuestionResponse): AskQuestionsUIState()

    /**
     * Represents a user-visible or diagnostic error message.
     *
     * @property message The error text that should be shown to the user.
     */
    data class Error(val message: String): AskQuestionsUIState()
}