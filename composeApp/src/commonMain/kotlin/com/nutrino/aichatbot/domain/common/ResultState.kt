package com.nutrino.aichatbot.domain.common

sealed class ResultState<out T>{
    data object isLoading: ResultState<Nothing>()
    data class Success<out T>(val data:T): ResultState<T>()
    data class Error<out T>(val message: String): ResultState<T>()
}