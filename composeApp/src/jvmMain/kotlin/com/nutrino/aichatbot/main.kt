package com.nutrino.aichatbot

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * Starts the desktop application on the JVM target.
 *
 * The function creates a Compose window and renders the shared [App] composable inside it.
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "AIChatBot",
    ) {
        App()
    }
}