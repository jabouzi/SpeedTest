package com.skanderjabouzi.speedtest

import io.ktor.utils.io.core.*
import okio.FileSystem
import okio.Path.Companion.toPath
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    fun getFile(): ByteArray {
        val path = "100MO.bat".toPath()

        val readmeContent = FileSystem.SYSTEM.read(path) {
            readUtf8()
        }

        return readmeContent.toByteArray()
    }
}

actual fun getPlatform(): Platform = IOSPlatform()

