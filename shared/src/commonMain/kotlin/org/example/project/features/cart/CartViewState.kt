package org.example.project.features.cart

import org.example.project.domain.models.CartItemModel
import org.example.project.domain.models.DeliveryType
import org.example.project.features.base.Reducer

data class CartViewState(
    val totalPrice: Int,
    val deliveryPrice: Int,
    val productsPrice: Int,
    val freeDeliveryPrice: Int?,
    val cartItems: List<CartItemModel>,
    val addressString: String,
    val deliveryType: DeliveryType,
    val continueText: String,
): Reducer.ViewState