package com.skanderjabouzi.speedtest.android

import com.skanderjabouzi.speedtest.model.entity.Speed

data class UploadSpeedState(
    val loading: Boolean = true,
    val error: Boolean = false,
    val speed: Speed? = null
)

data class DownloadSpeedState(
    val loading: Boolean = true,
    val error: Boolean = false,
    val speed: Speed? = null
)