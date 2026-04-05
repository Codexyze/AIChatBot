package com.nutrino.aichatbot

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nutrino.aichatbot.di.modules.appModules
import com.nutrino.aichatbot.presentation.screens.AskQuestionScreen
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

/**
 * Root composable for the shared application UI.
 *
 * The function applies the app theme, starts Koin dependency injection, and renders the main
 * Ask Question screen for every supported platform.
 */
@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinApplication(
            configuration = koinConfiguration(declaration = { modules(appModules) }),
            content = {
                AskQuestionScreen()
            })
    }
}