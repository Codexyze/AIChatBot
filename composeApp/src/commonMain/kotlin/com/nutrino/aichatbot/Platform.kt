package com.nutrino.aichatbot

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect val isDesktop: Boolean

