//
//  ProductCardDialog.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 05/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct ProductCardDialog: View {
    let component: ProductCardComponent
    @StateValue private var state: ProductCardViewState
    
    init(component: ProductCardComponent) {
        self.component = component
        _state = StateValue(self.component.state)
    }
    
    
    var body: some View {
        ProductCardDialogContent(
            isLoading: state.isLoading,
            productModel: state.product,
            onAddToCart: {
                component.onEvent(event: ProductCardViewEventOnAddToCart())
            },
            onRemoveFromCart: {
                component.onEvent(event: ProductCardViewEventOnRemoveFromCart())
            }
        )
    }
}
