package org.example.project.features.catalog

import org.example.project.features.base.Reducer

class CatalogReducer: Reducer<CatalogViewState, CatalogViewEvent, CatalogViewEffect> {
    override fun reduce(
        state: CatalogViewState,
        event: CatalogViewEvent
    ): CatalogViewState {
        return when (event) {
            is CatalogViewEvent.OnProductsLoaded -> {
                state.copy(
                    products = event.products
                )
            }
            is CatalogViewEvent.OnCategoryLoaded -> {
                state.copy(
                    title = event.category.title
                )
            }
            is CatalogViewEvent.OnCartLoaded -> {
                val newProducts = state.products.map { productModel ->
                    val cartItem = event.cartModel.items.firstOrNull { it.productId == productModel.id }
                    if (cartItem != null) {
                        productModel.copy(count = cartItem.quantity)
                    } else {
                        productModel.copy(count = 0)
                    }
                }

                state.copy(
                    amount = event.cartModel.totalPrice,
                    products = newProducts
                )
            }
            else -> state
        }
    }

    override fun handleEvent(event: CatalogViewEvent): CatalogViewEffect? {
        return when (event) {
            else -> null
        }
    }
}