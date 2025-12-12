package org.example.project.features.cart

import org.example.project.domain.models.CartItemModel
import org.example.project.domain.models.CartModel
import org.example.project.features.base.Reducer

sealed interface CartViewEvent: Reducer.ViewEvent {
    data object OnBackClick: CartViewEvent
    data object OnConfirmButtonClicked: CartViewEvent
    data class OnCartLoaded(val cartModel: CartModel): CartViewEvent
    data class OnAddToCart(val product: CartItemModel): CartViewEvent
    data class OnRemoveFromCart(val product: CartItemModel): CartViewEvent
    data class OnError(val message: String?) : CartViewEvent
    data class OnThrowError(val throwable: Throwable) : CartViewEvent
}