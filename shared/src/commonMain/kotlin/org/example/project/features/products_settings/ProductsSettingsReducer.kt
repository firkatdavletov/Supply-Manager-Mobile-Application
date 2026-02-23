package org.example.project.features.products_settings

import org.example.project.features.base.Reducer

class ProductsSettingsReducer :
    Reducer<ProductsSettingsViewState, ProductsSettingsViewEvent, ProductsSettingsViewEffect> {
    override fun reduce(
        state: ProductsSettingsViewState,
        event: ProductsSettingsViewEvent,
    ): ProductsSettingsViewState {
        return when (event) {
            ProductsSettingsViewEvent.OnBackClicked -> {
                state
            }

            ProductsSettingsViewEvent.OnAddProductClicked -> {
                state
            }

            is ProductsSettingsViewEvent.OnSearchQueryChanged -> {
                state.copy(searchQuery = event.value)
            }

            is ProductsSettingsViewEvent.OnProductClicked -> {
                state.copy(selectedProductId = event.productId)
            }

            is ProductsSettingsViewEvent.OnProductsLoaded -> {
                state.copy(
                    products = event.products,
                    isLoading = false,
                )
            }

            ProductsSettingsViewEvent.OnLoading -> {
                state.copy(isLoading = true)
            }

            is ProductsSettingsViewEvent.OnError -> {
                state.copy(isLoading = false)
            }

            is ProductsSettingsViewEvent.OnThrowError -> {
                state.copy(isLoading = false)
            }
        }
    }

    override fun handleEvent(event: ProductsSettingsViewEvent): ProductsSettingsViewEffect? {
        return null
    }
}
