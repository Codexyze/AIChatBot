package com.nutrino.aichatbot.di.modules

import com.nutrino.aichatbot.data.remote.repository.GeminiRepoImpl
import com.nutrino.aichatbot.domain.repository.GeminiRepository
import com.nutrino.aichatbot.domain.useCases.AskQuestionWithPromptUseCase
import com.nutrino.aichatbot.presentation.viewmodel.GeminiViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Application-wide Koin module for the shared Compose Multiplatform app.
 *
 * This module wires together the network client, repository, use case, and ViewModel so
 * common code can resolve dependencies consistently on every target.
 */
val appModules = module {
    single<GeminiRepository> {
        GeminiRepoImpl(httpClient = get())
    }
    single<HttpClient> {
        getKtorClient()
    }
    single<AskQuestionWithPromptUseCase> {
        AskQuestionWithPromptUseCase(geminiRepository = get())
    }
    viewModelOf(::GeminiViewModel)

}
private fun getKtorClient(): HttpClient{

/**
 * Creates the configured Ktor [HttpClient] used by the repository layer.
 *
 * The client is configured with generous timeouts and permissive JSON parsing so the app can
 * tolerate unknown fields from the Gemini API while still remaining stable.
 *
 * @return A ready-to-use Ktor HTTP client instance.
 */
     val httpclient= HttpClient{
        install(HttpTimeout) {
            requestTimeoutMillis = 300_000 // 120 seconds (2 minutes)
            connectTimeoutMillis = 300_000  // 60 seconds (1 minute)
            socketTimeoutMillis = 300_000  // 120 seconds (2 minutes)
        }
        install(ContentNegotiation){
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    coerceInputValues = true
                    ignoreUnknownKeys = true

                }
            )
        }
    }
    return httpclient
}