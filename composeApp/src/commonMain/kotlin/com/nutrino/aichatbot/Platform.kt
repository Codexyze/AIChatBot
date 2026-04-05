package com.nutrino.aichatbot

/**
 * Describes the current runtime platform in a shared, platform-agnostic way.
 *
 * Each platform-specific source set provides its own implementation so common code can use
 * platform details without depending on Android, iOS, JVM, JS, or Wasm APIs directly.
 */
interface Platform {
    /**
     * A human-readable platform name such as Android, iOS, Java, or a web target label.
     */
    val name: String
}

/**
 * Returns the current platform implementation for the active target.
 *
 * The concrete type is provided by each platform source set through `actual` declarations.
 */
expect fun getPlatform(): Platform

/**
 * Indicates whether the current target is a desktop runtime.
 *
 * The app uses this value to adjust layout width and spacing for desktop form factors.
 */
expect val isDesktop: Boolean

