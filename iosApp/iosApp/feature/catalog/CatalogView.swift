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
            products: state.products,
            amount: Int32(state.amount)
        ) { productModel in
                component.onEvent(event: CatalogViewEventOnAddToCart(product: productModel))
            } onRemove: { productModel in
                component.onEvent(event: CatalogViewEventOnRemoveFromCart(product: productModel))
            } onCartButtonClicked: {
                component.onEvent(event: CatalogViewEventOnCartButtonClicked())
            } onBackButtonClicked: {
                component.onEvent(event: CatalogViewEventOnBackClicked())
            }
    }
    
}
