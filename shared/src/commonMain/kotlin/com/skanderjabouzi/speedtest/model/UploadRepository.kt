package com.skanderjabouzi.speedtest.model

import com.skanderjabouzi.speedtest.model.entity.Speed
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.channelFlow
import kotlinx.datetime.Clock
import kotlin.random.Random

class UploadRepository {
    operator fun invoke() = channelFlow {
        val client = HttpClient(CIO)

        var startTime = Clock.System.now().nanosecondsOfSecond

        val bytes = ByteArray(100_000_000)
        Random(startTime).nextBytes(bytes)
        println(bytes.size)

        val fileName = "uplaod.dat.$startTime"

        println("startTime: $startTime")
        var prevTime = startTime;
        var prevSize = 0L;
        println(bytes)
        println(fileName)
        try {
            val response: HttpResponse = client.post("http://165.22.229.109:8181/upload") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("description", "Test file")
                            append("image", bytes, Headers.build {
                                append(HttpHeaders.ContentType, "application/binary")
                                append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                            })
                        },
                        boundary = "WebAppBoundary"
                    )
                )
                onUpload { bytesSentTotal, contentLength ->
                    println(Clock.System.now().nanosecondsOfSecond)
                    delay(1)
                    val newTime = Clock.System.now().nanosecondsOfSecond
                    val uploadedSize = bytesSentTotal
                    val averageSpeed = uploadedSize.toDouble() / (newTime - startTime)
                    //println("averageSpeed: $averageSpeed prevSize $prevSize")
                    val currentSpeed = (uploadedSize.toDouble() - prevSize) / (newTime - prevTime)
                    //println("new time: $newTime old time $prevTime")
                    prevTime = newTime
                    prevSize = uploadedSize
                    val timeRemaining = (uploadedSize - contentLength) / averageSpeed
                    val speed = Speed(
                        currentSpeed * 10000,
                        averageSpeed * 10000,
                        uploadedSize / 100,
                        timeRemaining
                    )
                    send(speed)
                    println("Sent $uploadedSize  total of $bytesSentTotal bytes from $contentLength averageSpeed $averageSpeed currentSpeed: $currentSpeed timeRemaining $timeRemaining")
                }
            }
            println(response.bodyAsText())
        } catch (e: HttpRequestTimeoutException) {
            println(e.stackTraceToString())
            val speed = Speed(0.0, 1.0, 1, 0.0)
            send(speed)
        }


    }
}