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
            companyName: state.companyName,
            createdAt: state.createdAt,
            orderStatus: state.status,
            customerName: state.customerName,
            customerEmail: state.customerEmail,
            customerPhone: state.customerPhone,
            deliveryType: state.deliveryType,
            address: state.addressString,
            comment: state.comment,
            orderItems: state.items,
            totalAmount: state.totalAmount,
            deliveryDate: state.deliveryDate,
            onBackButtonClicked: {
                component.onEvent(event: CurrentOrderViewEventOnBackClicked())
            },
            onTakeOrder: {
                component.onEvent(event: CurrentOrderViewEventOnTakeOrder())
            },
            onCompleteOrder: {
                component.onEvent(event: CurrentOrderViewEventOnCompleteOrder())
            },
            onCancelOrder: {
                component.onEvent(event: CurrentOrderViewEventOnCancelOrder())
            },
            onPendingOrder: {
                component.onEvent(event: CurrentOrderViewEventOnPendingOrder())
            }
        )
        .navigationBarBackButtonHidden(true)
    }
}
