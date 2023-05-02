package com.skanderjabouzi.speedtest.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skanderjabouzi.speedtest.model.DownloadRepository
import com.skanderjabouzi.speedtest.model.entity.Speed
import com.skanderjabouzi.speedtest.model.UploadRepository
import com.skanderjabouzi.speedtest.ui.SharedSpeedTestViewModel
import com.skanderjabouzi.speedtest.ui.SpeedTestViewModel
import com.skanderjabouzi.speedtest.util.CommonFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: SpeedTestViewModel, ViewModel() {

    private val vm = SharedSpeedTestViewModel()

    private val _uploadSpeed = MutableStateFlow<UploadSpeedState>(UploadSpeedState())
    val uploadSpeed: StateFlow<UploadSpeedState> = _uploadSpeed

    private val _downloadSpeed = MutableStateFlow<DownloadSpeedState>(DownloadSpeedState())
    val downloadSpeed: StateFlow<DownloadSpeedState> = _downloadSpeed

    val upload = UploadRepository()
    val download = DownloadRepository()

    fun startUploadTest() {
        viewModelScope.launch {
            upload().collect {
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

    override fun observeUpload(): CommonFlow<Speed> = vm.observeUpload()
    override fun observeDownload(): CommonFlow<Speed> = vm.observeDownload()
}