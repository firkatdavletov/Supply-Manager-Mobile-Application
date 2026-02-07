//
//  CatalogView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct CatalogView: View {
    let component: CatalogComponent
    
    @StateValue private var state: CatalogViewState
    
    init(component: CatalogComponent) {
        self.component = component
        _state = StateValue(self.component.state)
    }
    
    var body: some View {
        CatalogContent(
            title: state.title,
            isLoading: state.isLoading,
            categories: state.categories,
            products: state.products,
            amount: state.amount) {
                component.onEvent(event: CatalogViewEventOnCartButtonClicked())
            } onBackButtonClicked: {
                component.onEvent(event: CatalogViewEventOnBackClicked())
            } onCategoryClicked: { id in
                component.onEvent(event: CatalogViewEventOnCategoryClicked(categoryId: id))
            } onProductClicked: { product in
                component.onEvent(event: CatalogViewEventOnProductClicked(id: product))
            } onAddToCart: { id in
                component.onEvent(event: CatalogViewEventOnAddToCart(product: id))
            } onRemoveFromCart: { id in
                component.onEvent(event: CatalogViewEventOnRemoveFromCart(product: id))
            }
            .navigationBarBackButtonHidden()
    }
}
