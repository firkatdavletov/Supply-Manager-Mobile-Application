import SwiftUI
import Shared

struct ProductsSettingsView: View {
    let component: ProductsSettingsComponent

    @StateValue private var state: ProductsSettingsViewState

    init(component: ProductsSettingsComponent) {
        self.component = component
        _state = StateValue(component.state)
    }

    var body: some View {
        let normalizedQuery = state.searchQuery.trimmingCharacters(in: .whitespacesAndNewlines)
        let filteredProducts =
            normalizedQuery.isEmpty
            ? state.products
            : state.products.filter { product in
                product.title.localizedCaseInsensitiveContains(normalizedQuery) ||
                    product.description.localizedCaseInsensitiveContains(normalizedQuery)
            }

        ProductsSettingsContent(
            title: state.title,
            products: filteredProducts,
            searchQuery: state.searchQuery,
            selectedProductId: state.selectedProductId,
            isLoading: state.isLoading,
            onBack: {
                component.onEvent(event: ProductsSettingsViewEventOnBackClicked())
            },
            onAddProduct: {
                component.onEvent(event: ProductsSettingsViewEventOnAddProductClicked())
            },
            onSearchQueryChanged: { value in
                component.onEvent(event: ProductsSettingsViewEventOnSearchQueryChanged(value: value))
            },
            onProductClicked: { productId in
                component.onEvent(event: ProductsSettingsViewEventOnProductClicked(productId: productId))
            }
        )
        .navigationBarBackButtonHidden(true)
    }
}
