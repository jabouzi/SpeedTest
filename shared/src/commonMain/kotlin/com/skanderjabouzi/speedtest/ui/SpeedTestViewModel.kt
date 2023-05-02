package com.skanderjabouzi.speedtest.ui

import com.skanderjabouzi.speedtest.getPlatform
import com.skanderjabouzi.speedtest.model.entity.Speed
import com.skanderjabouzi.speedtest.util.CommonFlow

/**
 * Interface describing the ViewModel on both the `shared` and `platform` side.
 */
interface SpeedTestViewModel: SharedViewModel {
    val platform: String
        get() = getPlatform().name
    fun observeUpload(): CommonFlow<Speed>
    fun observeDownload(): CommonFlow<Speed>
}