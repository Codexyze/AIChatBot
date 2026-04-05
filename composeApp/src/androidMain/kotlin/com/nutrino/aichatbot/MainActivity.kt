package com.nutrino.aichatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Android entry activity for the Compose Multiplatform app.
 *
 * The activity initializes edge-to-edge rendering and hosts the shared [App] composable as the
 * root UI for Android devices.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is created.
     *
     * The method enables edge-to-edge content, invokes the base activity lifecycle setup, and
     * attaches the shared Compose UI to the activity window.
     *
     * @param savedInstanceState Previously saved instance state for activity recreation, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

/**
 * Android preview entry for the shared application UI.
 *
 * This preview lets the IDE render the shared app composable without launching the full app.
 */
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}