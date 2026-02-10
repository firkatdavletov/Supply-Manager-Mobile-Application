//
//  CartView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 11.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct CartView: View {
    let component: CartComponent

    @StateValue private var state: CartViewState

    init(component: CartComponent) {
        self.component = component
        _state = StateValue(component.state)
    }

    var body: some View {
        CartContent(
            cartItems: state.cartItems,
            productPrice: state.productsPrice.asInt64,
            deliveryPrice: state.deliveryPrice.asInt64,
            totalAmount: state.totalPrice.asInt64,
            freeDeliveryPrice: state.freeDeliveryPrice.asInt64,
            deliveryType: state.deliveryType,
            onAddToCart: onAddToCart,
            onRemoveFromCart: onRemoveFromCart,
            onBack: onBack,
            onConfirm: onConfirm
        )
        .navigationBarBackButtonHidden(true)
    }
}

private extension CartView {
    func onAddToCart(_ item: CartItemModel) {
        component.onEvent(event: CartViewEventOnAddToCart(product: item))
    }

    func onRemoveFromCart(_ item: CartItemModel) {
        component.onEvent(event: CartViewEventOnRemoveFromCart(product: item))
    }

    func onBack() {
        component.onEvent(event: CartViewEventOnBackClick())
    }

    func onConfirm() {
        component.onEvent(event: CartViewEventOnConfirmButtonClicked())
    }
}
