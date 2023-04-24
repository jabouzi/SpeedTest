package com.skanderjabouzi.speedtest

import kotlinx.datetime.Clock


class  Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        val nano =  Clock.System.now().nanosecondsOfSecond
        return "Hello, ${platform.name} == ${nano}!"
    }
}