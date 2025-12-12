import SwiftUI
import Shared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    @Environment(\.scenePhase)
    var scenePhase: ScenePhase
    
    var rootHolder: RootHolder { appDelegate.rootHolder }
    
    var body: some Scene {
        WindowGroup {
            RootView(root: rootHolder.root)
                .onChange(of: scenePhase) { newPhase in
                    switch (newPhase) {
                    case .background: LifecycleRegistryExtKt.stop(rootHolder.lifeCycle)
                    case .inactive: LifecycleRegistryExtKt.pause(rootHolder.lifeCycle)
                    case .active: LifecycleRegistryExtKt.resume(rootHolder.lifeCycle)
                    @unknown default: break
                    }
                }
        }
    }
    
    init() {
        HelperKtKt.doInitKoin()
    }
}
