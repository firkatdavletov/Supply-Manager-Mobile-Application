//
//  SwiftUIView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 24/07/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct CurrentOrderView: View {
    let component: CurrentOrderComponent
    @StateValue private var state: CurrentOrderViewState
    
    init(component: CurrentOrderComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    var body: some View {
        CurrentOrderContent(
            title: state.number,
            statusTitle: state.status,
            deliveryType: state.deliveryType,
            address: state.addressString,
            comment: state.comment,
            orderItems: state.items,
            productPrice: Int(state.productsPrice),
            deliveryPrice: Int(state.deliveryPrice),
            totalPrice: Int(state.totalAmount)
        ) {
            component.onEvent(event: CurrentOrderViewEventOnBackClicked())
        }
        .navigationBarBackButtonHidden(true)
    }
}
