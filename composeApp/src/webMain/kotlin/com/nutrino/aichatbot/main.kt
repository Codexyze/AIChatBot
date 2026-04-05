package com.nutrino.aichatbot

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

/**
 * Starts the web application entry point for the browser target.
 *
 * The viewport hosts the shared [App] composable inside the page container.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        App()
    }
}