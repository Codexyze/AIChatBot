package com.nutrino.aichatbot

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nutrino.aichatbot.di.modules.appModules
import com.nutrino.aichatbot.presentation.screens.AskQuestionScreen
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

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