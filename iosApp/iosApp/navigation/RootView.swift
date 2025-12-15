import SwiftUI
import Shared

struct RootView: View {
    let root: RootComponent
    
    var body: some View {
        StackView(
            stackValue: StateValue(root.childStack),
            getTitle: { a in
                return ""
            },
            onBack: { index in
                root.onBackClicked(toIndex: index)
            },
            childContent: { child in
                switch (child) {
                case let child as RootComponentChild.Launch: LaunchView(component: child.component)
                case let child as RootComponentChild.AppIntroduction: AppIntroductionView(component: child.component)
                case let child as RootComponentChild.Authorization: AuthorizationView(root: child.component)
                case let child as RootComponentChild.SelectAddress: MapView(component: child.component)
                case let child as RootComponentChild.Home: HomeView(component: child.component)
                default: EmptyView()
                }
            }
        )
    }
}
