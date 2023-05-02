import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject var vm = IOSSpeedViewViewModel()
	let greet = Greeting().greet()

	var body: some View {
		Text(greet)
        VStack(alignment: .leading) {
            Button("Upload") {
                vm.startUpload()
            }

            Text("\(vm.upload.averageSpeed)")
                .font(.largeTitle)
        }
        VStack(alignment: .leading) {
            Button("Downlaod") {
                vm.startDownload()
            }

            Text("\(vm.download.averageSpeed)")
                .font(.largeTitle)
        }
        .onAppear {
        }
        .onDisappear {
            vm.stop()
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
