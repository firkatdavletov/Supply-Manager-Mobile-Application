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
            productPrice: state.productsPrice,
            deliveryPrice: state.deliveryPrice,
            totalAmount: state.totalPrice,
            freeDeliveryPrice: state.freeDeliveryPrice != nil ? Int32(truncating: state.freeDeliveryPrice!) : nil,
            deliveryType: state.deliveryType,
            onAddToCart: { item in
                component.onEvent(event: CartViewEventOnAddToCart(product: item))
            },
            onRemoveFromCart: { item in
                component.onEvent(event: CartViewEventOnRemoveFromCart(product: item))
            },
            onBack: {
                component.onEvent(event: CartViewEventOnBackClick())
            },
            onConfirm: {
                component.onEvent(event: CartViewEventOnConfirmButtonClicked())
            }
        )
        .navigationBarBackButtonHidden(true)
    }
}
