@file:Suppress("unused", "UNUSED")

package com.nutrino.aichatbot

import androidx.compose.ui.window.ComposeUIViewController

/**
 * Creates the UIKit view controller that hosts the shared Compose UI on iOS.
 *
 * @return A `UIViewController` configured to render the shared [App] composable.
 */
@Suppress("unused", "UNUSED")
fun MainViewController() = ComposeUIViewController { App() }