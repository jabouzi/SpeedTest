package com.skanderjabouzi.speedtest.ui

import com.skanderjabouzi.speedtest.model.DownloadRepository
import com.skanderjabouzi.speedtest.model.UploadRepository
import com.skanderjabouzi.speedtest.model.entity.Speed
import com.skanderjabouzi.speedtest.util.CommonFlow
import com.skanderjabouzi.speedtest.util.asCommonFlow

/**
 * Class for the shared parts of the ViewModel.
 *
 * ViewModels are split into two parts:
 * - `SharedViewModel`, which contains the business logic and communication with
 *   the repository / model layer.
 * - `PlatformViewModel`, which is only a thin wrapper for hooking the SharedViewModel
 *   up to either SwiftUI (through `@ObservedObject`) or to Compose (though Flows).
 *
 * The boundary between these two classes must only be [CommonFlow]'s, which emit
 * on the UI or Main thread.
 *
 * This allows the UI to be fully tested by injecting a mocked ViewModel on the
 * platform side.
 */
class SharedSpeedTestViewModel: SpeedTestViewModel {

    // Implementation note: With a ViewModel this simple, just merging it with
    // Repository would probably be simpler, but by splitting the Repository
    // and ViewModel, we only need to enforce CommonFlows at the boundary, and
    // it means the CounterViewModel can be mocked easily in the View Layer.
    private val uploadRepository = UploadRepository()
    private val downloaddRepository = DownloadRepository()


    override fun observeUpload(): CommonFlow<Speed> {
        return uploadRepository().asCommonFlow()
    }

    override fun observeDownload(): CommonFlow<Speed> {
        return downloaddRepository().asCommonFlow()
    }
}
