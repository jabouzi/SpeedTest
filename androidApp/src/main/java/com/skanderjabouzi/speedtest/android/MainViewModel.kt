package com.skanderjabouzi.speedtest.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skanderjabouzi.speedtest.Download
import com.skanderjabouzi.speedtest.Speed
import com.skanderjabouzi.speedtest.Upload
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _uploadSpeed = MutableStateFlow<UploadSpeedState>(UploadSpeedState())
    val uploadSpeed: StateFlow<UploadSpeedState> = _uploadSpeed

    private val _downloadSpeed = MutableStateFlow<DownloadSpeedState>(DownloadSpeedState())
    val downloadSpeed: StateFlow<DownloadSpeedState> = _downloadSpeed

    val upload = Upload()
    val download = Download()

    fun startUploadTest(fileName: String, bytes: ByteArray) {
        viewModelScope.launch {
            upload(fileName, bytes).collect {
                handleUploadSuccess(it)
            }
        }
    }

    fun startDownloadTest() {
        viewModelScope.launch {
            download().collect {
                handleDownloadSuccess(it)
            }
        }
    }

    private fun handleUploadSuccess(speedState: Speed) {
        _uploadSpeed.value = UploadSpeedState(loading = false, speed = speedState)
    }

    private fun handleUploadFailure() {
        _uploadSpeed.value = UploadSpeedState(loading = false, error = true)
    }

    private fun handleDownloadSuccess(speedState: Speed) {
        _downloadSpeed.value = DownloadSpeedState(loading = false, speed = speedState)
    }

    private fun handleDownloadFailure() {
        _downloadSpeed.value = DownloadSpeedState(loading = false, error = true)
    }
}