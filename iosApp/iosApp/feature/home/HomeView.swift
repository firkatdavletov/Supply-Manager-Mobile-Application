//
//  HomeView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//P

import SwiftUI
import Shared

struct HomeView: View {
    let component: HomeComponent
    
    @StateValue private var state: HomeViewState
    
    init(component: HomeComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    
    var body: some View {
        HomeContent(
            userName: state.userName,
            addressString: state.deliveryAddress,
            deliveryInfo: state.deliveryInfo,
            currentOrders: state.currentOrders,
            categories: state.categories,
            totalAmount: Float(state.amount),
            storeIsClosed: state.storeIsClosed,
            onChangeAddressClicked: {
                component.onEvent(event: HomeViewEventOnAddressClicked())
            },
            onCategoryClicked: { categoryModel in
                component.onEvent(event: HomeViewEventOnCategoryClicked(categoryId: categoryModel.id, categoryTitle: categoryModel.title))
            },
            onCartButtonClicked: {
                component.onEvent(event: HomeViewEventOnCartButtonClicked())
            },
            onPersonClicked: {
                component.onEvent(event: HomeViewEventOnProfileClicked())
            },
            onOrderClicked: { id in
                component.onEvent(event: HomeViewEventOnOrderClicked(id: id))
            }
        )
        .navigationBarBackButtonHidden(true)
    }
}
