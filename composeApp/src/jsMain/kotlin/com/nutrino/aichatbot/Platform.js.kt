package com.nutrino.aichatbot

/**
 * Kotlin/JS implementation of the shared [Platform] abstraction.
 */
class JsPlatform: Platform {
    override val name: String = "Web with Kotlin/JS"
}

/**
 * Returns the Kotlin/JS-specific platform implementation.
 */
actual fun getPlatform(): Platform = JsPlatform()

/**
 * Kotlin/JS is not treated as a desktop target in this app.
 */
actual val isDesktop: Boolean = false
