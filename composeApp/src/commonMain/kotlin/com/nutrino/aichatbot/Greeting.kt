package com.nutrino.aichatbot

/**
 * Simple shared helper that formats a greeting message for the current platform.
 *
 * The class demonstrates how common code can read platform information from expect/actual
 * declarations and build a string without platform-specific branching in the caller.
 */
class Greeting {
    private val platform = getPlatform()

    /**
     * Returns a short greeting that includes the current platform name.
     *
     * @return A string in the form `Hello, <platform name>!`.
     */
    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}