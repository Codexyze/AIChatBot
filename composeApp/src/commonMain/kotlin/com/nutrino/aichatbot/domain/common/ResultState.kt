package com.nutrino.aichatbot.domain.common

/**
 * Represents the state of an operation that can be loading, successful, or failed.
 *
 * This sealed class is used by the domain and presentation layers to expose a consistent
 * result contract for asynchronous work such as network requests.
 *
 * @param T The type of data returned when the operation succeeds.
 */
sealed class ResultState<out T>{
    /**
     * Indicates that the operation is currently in progress and has not produced a result yet.
     */
    data object isLoading: ResultState<Nothing>()

    /**
     * Represents a successful operation result.
     *
     * @property data The data returned by the operation.
     */
    data class Success<out T>(val data:T): ResultState<T>()

    /**
     * Represents a failed operation result.
     *
     * @property message A human-readable description of the failure.
     */
    data class Error<out T>(val message: String): ResultState<T>()
}