package org.example.project.features.cart

import org.example.project.domain.utils.AddressUtility
import org.example.project.features.base.Reducer

class CartReducer : Reducer<CartViewState, CartViewEvent, CartViewEffect> {
    override fun reduce(
        state: CartViewState,
        event: CartViewEvent,
    ): CartViewState {
        return when (event) {
            is CartViewEvent.OnCartLoaded -> {
                state.copy(
                    totalPrice = event.cartModel.totalPrice,
                    deliveryPrice = event.cartModel.deliveryInfo.deliveryPrice,
                    productsPrice = event.cartModel.items.sumOf { it.price * it.quantity },
                    freeDeliveryPrice = event.cartModel.deliveryInfo.freeDeliveryPrice,
                    cartItems = event.cartModel.items,
                    addressString = event.cartModel.deliveryAddress?.let {
                        AddressUtility.makeAddressString(it)
                    } ?: "",
                    deliveryType = event.cartModel.deliveryType,
                    continueText = if (event.cartModel.deliveryAddress != null) {
                        "ПРОДОЛЖИТЬ"
                    } else {
                        "ВЫБРАТЬ АДРЕС"
                    },
                )
            }

            else -> {
                state
            }
        }
    }

    override fun handleEvent(event: CartViewEvent): CartViewEffect? {
        TODO("Not yet implemented")
    }
}