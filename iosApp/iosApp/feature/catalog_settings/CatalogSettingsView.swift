import SwiftUI
import Shared

struct CatalogSettingsView: View {
    let component: CatalogSettingsComponent

    @StateValue private var state: CatalogSettingsViewState

    init(component: CatalogSettingsComponent) {
        self.component = component
        _state = StateValue(component.state)
    }

    var body: some View {
        CatalogSettingsContent(
            title: state.title,
            items: state.items,
            selectedItem: state.selectedItem,
            onBack: {
                component.onEvent(event: CatalogSettingsViewEventOnBackClicked())
            },
            onItemClicked: { item in
                component.onEvent(event: CatalogSettingsViewEventOnMenuItemClicked(item: item))
            }
        )
        .navigationBarBackButtonHidden(true)
    }
}
