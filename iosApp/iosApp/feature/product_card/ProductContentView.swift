//
//  ProductContentView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 05/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct ProductContentView: View {

    let product: Shared.ProductModel
    let onAddToCart: () -> Void
    let onRemoveFromCart: () -> Void

    var body: some View {
        ZStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {

                    ProductImageView(imageUrl: product.imageUrl)

                    VStack(alignment: .leading, spacing: 8) {
                        Text(product.title)
                            .font(.title2)
                            .fontWeight(.semibold)

                        Text(priceText)
                            .font(.title3)
                            .fontWeight(.bold)
                        
                        if (product.description_ != nil) {
                            Text(product.description_!)
                                .font(.body)
                                .foregroundColor(.secondary)
                        }
                    }
                    .padding(.horizontal)
                }
            }
            VStack {
                Spacer()
                if (product.count == 0) {
                    RoundedButton(
                        title: "Добавить в корзину",
                        onClick: {
                            onAddToCart()
                        },
                        enabled: true
                    )
                } else {
                    ProductCardButton(
                        quantity: Int(product.count),
                        onAdd: onAddToCart,
                        onRemove: onRemoveFromCart
                    )
                }
            }
            .padding(.horizontal)
        }
    }

    private var priceText: String {
        String(format: "%.0f руб", product.price)
    }
}
