package com.charlesmccullough.dailypulse

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform