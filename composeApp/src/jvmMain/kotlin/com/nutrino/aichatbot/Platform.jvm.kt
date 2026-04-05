package com.nutrino.aichatbot

/**
 * JVM implementation of the shared [Platform] abstraction.
 */
class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

/**
 * Returns the JVM-specific platform implementation.
 */
actual fun getPlatform(): Platform = JVMPlatform()

/**
 * JVM is treated as a desktop target in this app.
 */
actual val isDesktop: Boolean = true
