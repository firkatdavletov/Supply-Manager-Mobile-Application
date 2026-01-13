package org.example.project.dialogs.product_card

import org.example.project.domain.models.CartModel
import org.example.project.domain.models.ProductModel
import org.example.project.features.base.Reducer

sealed interface ProductCardViewEvent : Reducer.ViewEvent {
    data class OnProductLoaded(val product: ProductModel) : ProductCardViewEvent
    data class OnError(val error: String) : ProductCardViewEvent
    data class OnThrowError(val throwable: Throwable) : ProductCardViewEvent
    data class OnCartLoaded(val cart: CartModel) : ProductCardViewEvent
    data object OnAddToCart : ProductCardViewEvent
    data object OnRemoveFromCart : ProductCardViewEvent
}