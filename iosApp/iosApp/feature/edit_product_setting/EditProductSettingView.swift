import SwiftUI
import Shared

struct EditProductSettingView: View {
    let component: EditProductComponent

    @StateValue private var state: EditProductSettingViewState

    init(component: EditProductComponent) {
        self.component = component
        _state = StateValue(component.state)
    }

    var body: some View {
        EditProductSettingContent(
            title: state.title,
            name: state.name,
            description: state.description_,
            price: state.price,
            imageUrl: state.imageUrl,
            isLoading: state.isLoading,
            onBack: {
                component.onEvent(event: EditProductSettingViewEventOnBackClicked())
            },
            onNameChanged: { value in
                component.onEvent(event: EditProductSettingViewEventOnNameChanged(value: value))
            },
            onDescriptionChanged: { value in
                component.onEvent(event: EditProductSettingViewEventOnDescriptionChanged(value: value))
            },
            onPriceChanged: { value in
                component.onEvent(event: EditProductSettingViewEventOnPriceChanged(value: value))
            },
            onImageUrlChanged: { value in
                component.onEvent(event: EditProductSettingViewEventOnImageUrlChanged(value: value))
            },
            onSave: {
                component.onEvent(event: EditProductSettingViewEventOnSaveClicked())
            }
        )
        .navigationBarBackButtonHidden(true)
    }
}
