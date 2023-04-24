package com.skanderjabouzi.speedtest

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform