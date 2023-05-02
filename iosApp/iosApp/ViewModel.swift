//
//  ViewModel.swift
//  iosApp
//
//  Created by Christian Melchior on 24/09/2021.
//
import Foundation
import Combine
import shared

// Generic Observable View Model, making it easier to control the lifecycle
// of multiple Flows.
class ObservableViewModel {
    private var jobs = Array<Closeable>() // List of Kotlin Coroutine Jobs

    func addObserver(observer: Closeable) {
        jobs.append(observer)
    }
    
    func stop() {
        jobs.forEach { job in job.close() }
    }
}

class IOSSpeedViewViewModel: ObservableViewModel, ObservableObject {
    @Published var upload: Speed = Speed(
        currentSpeed: 0.0,
        averageSpeed: 0.0,
        currentSize: 0,
        timeRemaining: 0.0
    )
    
    @Published var download: Speed = Speed(
        currentSpeed: 0.0,
        averageSpeed: 0.0,
        currentSize: 0,
        timeRemaining: 0.0
    )
    
    let uploadRepo = UploadRepository()
    let downloadRepo = DownloadRepository()

    private let vm: SpeedTestViewModel = SharedSpeedTestViewModel()
    
    func startUpload() {
        addObserver(observer: vm.observeUpload().watch { uploadValue in
            self.upload = uploadValue! as Speed
        })
    }
    
    func startDownload() {
        addObserver(observer: vm.observeDownload().watch { downloadValue in
            self.download = downloadValue! as Speed
        })
    }
    
    override func stop() {
        super.stop()
        vm.close()
    }
}
