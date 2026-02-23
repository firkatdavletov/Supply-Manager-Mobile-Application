import SwiftUI
import Shared

struct EditCategoryView: View {
    let component: EditCategoryComponent

    @StateValue private var state: EditCategoryViewState

    init(component: EditCategoryComponent) {
        self.component = component
        _state = StateValue(component.state)
    }

    var body: some View {
        EditCategoryContent(
            title: state.title,
            name: state.name,
            imageUrl: state.imageUrl,
            isLoading: state.isLoading,
            onBack: {
                component.onEvent(event: EditCategoryViewEventOnBackClicked())
            },
            onNameChanged: { value in
                component.onEvent(event: EditCategoryViewEventOnNameChanged(value: value))
            },
            onImageUrlChanged: { value in
                component.onEvent(event: EditCategoryViewEventOnImageUrlChanged(value: value))
            },
            onSave: {
                component.onEvent(event: EditCategoryViewEventOnSaveClicked())
            }
        )
        .navigationBarBackButtonHidden(true)
    }
}
