package com.nutrino.aichatbot

import platform.UIKit.UIDevice

/**
 * iOS implementation of the shared [Platform] abstraction.
 */
class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

/**
 * Returns the iOS-specific platform implementation.
 */
actual fun getPlatform(): Platform = IOSPlatform()

/**
 * iOS is not treated as a desktop target in this app.
 */
actual val isDesktop: Boolean = false
