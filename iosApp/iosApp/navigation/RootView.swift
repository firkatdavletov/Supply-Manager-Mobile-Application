import SwiftUI
import Shared

struct RootView: View {
    let root: RootComponent
    
    @StateObject private var effectObserver = EffectObserver()
    @State private var showToast = false
    @State private var toastMessage = ""
    @State private var activeDialog: RootComponentBottomChild?

    
    @StateValue
    private var dialogSlot: ChildSlot<AnyObject, RootComponentBottomChild>
    

    init(root: RootComponent) {
        self.root = root
        _dialogSlot = StateValue(root.dialogStack)
    }
    
    
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
                case let child as RootComponentChild.Catalog: CatalogView(component: child.component)
                case let child as RootComponentChild.Cart: CartView(component: child.component)
                case let child as RootComponentChild.Payment: PaymentView(component: child.component)
                case let child as RootComponentChild.SignIn: SignInView(component: child.component)
                case let child as RootComponentChild.Verification: VerificationView(component: child.component)
                case let child as RootComponentChild.Profile: ProfileView(component: child.component)
                case let child as RootComponentChild.Settings: SettingsView(component: child.component)
                case let child as RootComponentChild.CurrentOrder: CurrentOrderView(component: child.component)
                case let child as RootComponentChild.SearchAddress: SearchAddressView(component: child.component)
                default: EmptyView()
                }
            }
        )
        .toastBanner(
            isPresented: $showToast,
            message: toastMessage,
            type: ToastType.error
        )
        .onAppear {
            effectObserver.start(component: root.snackBarManager)
        }
        .onDisappear {
            effectObserver.stop()
        }
        .onReceive(effectObserver.$effect.dropFirst()) { effect in
            guard let effect = effect else { return }
        
            toastMessage = effect
            showToast = true
        }
        .sheet(
            isPresented: .constant(dialogSlot.child != nil),
            onDismiss: {
                root.dismissDialog()
            }
        ) {
            if let dialog = dialogSlot.child?.instance {
                dialogView(dialog)
            }
        }
    }
    
    @ViewBuilder
    private func dialogView(_ dialog: RootComponentBottomChild) -> some View {
        switch dialog {
        case let dialog as RootComponentBottomChild.ProductCard:
            ProductCardDialog(component: dialog.component)
        case let dialog as RootComponentBottomChild.DeleteUser:
            DeleteUserDialog(component: dialog.component)
        case let dialog as RootComponentBottomChild.LogoutUser:
            LogoutUserDialog(component: dialog.component)

        default:
            EmptyView()
        }
    }
}
