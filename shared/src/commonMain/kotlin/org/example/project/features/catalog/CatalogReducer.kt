package org.example.project.features.catalog

import org.example.project.features.base.Reducer

class CatalogReducer : Reducer<CatalogViewState, CatalogViewEvent, CatalogViewEffect> {
    override fun reduce(
        state: CatalogViewState,
        event: CatalogViewEvent,
    ): CatalogViewState {
        return when (event) {
            is CatalogViewEvent.OnCategoriesLoaded -> {
                state.copy(
                    title = "Категории",
                    isLoading = false,
                    categories = event.categories,
                )
            }

            is CatalogViewEvent.OnCategoryLoaded -> {
                state.copy(
                    title = event.categoryModel.title,
                    isLoading = false,
                    categories = event.categoryModel.children,
                    products = event.categoryModel.products,
                )
            }

            is CatalogViewEvent.OnCartLoaded -> {
                val cartItems = event.cartModel.items

                state.copy(
                    products = state.products.map { product ->
                        product.copy(
                            count = cartItems.firstOrNull { it.productId == product.id }?.quantity ?: 0,
                        )
                    },
                    amount = event.cartModel.totalPrice,
                )
            }

            CatalogViewEvent.OnBackClicked -> {
                state
            }

            CatalogViewEvent.OnCartButtonClicked -> {
                state
            }

            is CatalogViewEvent.OnCategoryClicked -> {
                state
            }

            is CatalogViewEvent.OnAddToCart -> {
                state
            }

            is CatalogViewEvent.OnRemoveFromCart -> {
                state
            }

            is CatalogViewEvent.OnProductClicked -> {
                state
            }
        }
    }

    override fun handleEvent(event: CatalogViewEvent): CatalogViewEffect? {
        return when (event) {
            else -> null
        }
    }
}