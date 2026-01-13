//
//  ProductCardDialogContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 05/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct ProductCardDialogContent: View {
    let isLoading: Bool
    let productModel: Shared.ProductModel?
    let onAddToCart: () -> Void
    let onRemoveFromCart: () -> Void
    
    var body: some View {
        Group {
            if isLoading {
                ProgressView()
            } else if let product = productModel {
                ProductContentView(
                    product: product,
                    onAddToCart: onAddToCart,
                    onRemoveFromCart: onRemoveFromCart
                )
            } else {
                Text("Продукт недоступен")
            }
        }
        .presentationDetents([.large])
        .presentationDragIndicator(.visible)
    }
}
