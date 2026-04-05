package com.nutrino.aichatbot

/**
 * Kotlin/Wasm implementation of the shared [Platform] abstraction.
 */
class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

/**
 * Returns the Kotlin/Wasm-specific platform implementation.
 */
actual fun getPlatform(): Platform = WasmPlatform()

/**
 * Kotlin/Wasm is not treated as a desktop target in this app.
 */
actual val isDesktop: Boolean = false
