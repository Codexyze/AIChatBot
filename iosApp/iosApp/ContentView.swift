import UIKit
import SwiftUI
import ComposeApp

/// Bridges the shared Compose UI into SwiftUI by hosting the Kotlin view controller.
struct ComposeView: UIViewControllerRepresentable {
    /// Creates the underlying view controller for the shared Compose screen.
    ///
    /// - Parameter context: The SwiftUI context used during view creation.
    /// - Returns: A `UIViewController` produced by the Kotlin `MainViewController` entry point.
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    /// Updates the hosted view controller when SwiftUI refreshes the view hierarchy.
    ///
    /// - Parameters:
    ///   - uiViewController: The hosted UIKit controller.
    ///   - context: The SwiftUI update context.
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

/// The top-level SwiftUI container for the iOS application.
struct ContentView: View {
    /// The body of the view, which renders the shared Compose UI full-screen.
    var body: some View {
        ComposeView()
            .ignoresSafeArea()
    }
}
