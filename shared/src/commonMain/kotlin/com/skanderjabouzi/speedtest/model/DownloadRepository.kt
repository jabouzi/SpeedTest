package com.skanderjabouzi.speedtest.model

import com.skanderjabouzi.speedtest.model.entity.Speed
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.prepareGet
import io.ktor.http.contentLength
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
import kotlinx.coroutines.flow.channelFlow
import kotlinx.datetime.Clock

class DownloadRepository {
    operator fun invoke() = channelFlow {
        val DEFAULT_BUFFER_SIZE: Int = 8 * 1024
        val client = HttpClient(CIO)
        //val file = File.createTempFile("files", "index", File("/tmp"))

        var startTime = Clock.System.now().nanosecondsOfSecond
        //println("file: $file")
        var prevTime = startTime;
        var prevSize = 0L
        var downloadedSize = 0L

        client.prepareGet("http://skanderjabouzi.com/10MO.bat").execute { httpResponse ->
            val channel: ByteReadChannel = httpResponse.body()
            while (!channel.isClosedForRead) {
                val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                while (!packet.isEmpty) {
                    val bytes = packet.readBytes()
                    //file.appendBytes(bytes)
                    val downloadSize = httpResponse.contentLength() ?: 0

                    downloadedSize += bytes.size
                    val averageSpeed =
                        downloadedSize.toDouble() / ((Clock.System.now().nanosecondsOfSecond) - startTime)
                    //println("averageSpeed: $averageSpeed prevSize $prevSize")
                    val currentSpeed =
                        (downloadedSize.toDouble() - prevSize) / ((Clock.System.now().nanosecondsOfSecond) - prevTime)
                    //println("size: ${downloaded_size.toDouble() - prevSize} time ${(System.nanoTime()) - prevTime}")
                    prevTime = (Clock.System.now().nanosecondsOfSecond)
                    prevSize = downloadedSize
                    //println("averageSpeed: $averageSpeed prevSize $prevSize currentSpeed $currentSpeed")

                    val timeRemaining = (downloadedSize - downloadSize) / averageSpeed
                    val speed = Speed(currentSpeed * 10000, averageSpeed * 10000, downloadedSize / 100, timeRemaining)
                    send(speed)
                    println("Received ${bytes.size} bytes from ${httpResponse.contentLength()} averageSpeed: $averageSpeed currentSpeed: $currentSpeed timeRemaining: $timeRemaining")
                }
            }
            //println("A file saved to ${file.path}")
        }
    }
}