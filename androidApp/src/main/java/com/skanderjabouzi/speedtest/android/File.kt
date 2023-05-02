package com.skanderjabouzi.speedtest.android

import android.content.Context
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


object FileHelper {

    @Throws(IOException::class)
    fun readAsset(context: Context, filename: String?): ByteArray? {
        val inputStream = context.resources.assets.open(filename!!)
        return try {
            readAllBytes(inputStream)
        } finally {
            inputStream.close()
        }
    }

    @Throws(IOException::class)
    fun readAllBytes(inputStream: InputStream): ByteArray? {
        val out = ByteArrayOutputStream()
        copyAllBytes(inputStream, out)
        return out.toByteArray()
    }

    /**
     * Copies all available data from in to out without closing any stream.
     *
     * @return number of bytes copied
     */
    @Throws(IOException::class)
    fun copyAllBytes(inputStream: InputStream, out: OutputStream): Int {
        var byteCount = 0
        val buffer = ByteArray(4096)
        while (true) {
            val read = inputStream.read(buffer)
            if (read == -1) {
                break
            }
            out.write(buffer, 0, read)
            byteCount += read
        }
        return byteCount
    }
 }