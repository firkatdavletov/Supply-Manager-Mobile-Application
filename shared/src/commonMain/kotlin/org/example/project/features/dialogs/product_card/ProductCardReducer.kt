package org.example.project.features.dialogs.product_card

import org.example.project.features.base.Reducer

class ProductCardReducer : Reducer<ProductCardViewState, ProductCardViewEvent, ProductCardViewEffect> {
    override fun reduce(
        state: ProductCardViewState,
        event: ProductCardViewEvent
    ): ProductCardViewState {
        return when (event) {
            is ProductCardViewEvent.OnError -> state.copy(isLoading = false)
            is ProductCardViewEvent.OnProductLoaded -> state.copy(
                isLoading = false,
                product = event.product
            )
            is ProductCardViewEvent.OnThrowError -> state.copy(
                isLoading = false
            )
            is ProductCardViewEvent.OnCartLoaded -> {
                val cartItem = event.cart.items.find { it.productId == state.product?.id }

                state.copy(
                    product = state.product?.copy(count = cartItem?.quantity ?: 0)
                )
            }
            else -> state
        }
    }

    override fun handleEvent(event: ProductCardViewEvent): ProductCardViewEffect? {
        TODO("Not yet implemented")
    }
}