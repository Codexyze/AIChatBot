package com.nutrino.aichatbot

import android.os.Build

/**
 * Android implementation of the shared [Platform] abstraction.
 */
class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

/**
 * Returns the Android-specific platform implementation.
 */
actual fun getPlatform(): Platform = AndroidPlatform()

/**
 * Android is not treated as a desktop target in this app.
 */
actual val isDesktop: Boolean = false
