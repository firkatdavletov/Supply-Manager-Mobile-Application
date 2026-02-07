//
//  CatalogContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 16/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct CatalogContent: View {
    let title: String
    let isLoading: Bool
    let categories: [CategoryModel]
    let products: [Shared.ProductModel]
    let amount: Int64
    let onCartButtonClicked: () -> Void
    let onBackButtonClicked: () -> Void
    let onCategoryClicked: (Int64) -> Void
    let onProductClicked: (Int64) -> Void
    let onAddToCart: (Shared.ProductModel) -> Void
    let onRemoveFromCart: (Shared.ProductModel) -> Void
    
    var body: some View {
        VStack(spacing: 0) {
            CatalogTopBar(
                title: title,
                onBack: onBackButtonClicked
            )
            Text("\(amount)")

            ScrollView {
                VStack {
                    if (!categories.isEmpty) {
                        LazyVStack {
                            ForEach(categories, id: \.id) { category in
                                CategoryCardView(
                                    title: category.title,
                                    imageUrl: category.imageUrl
                                )
                                .onTapGesture {
                                    onCategoryClicked(category.id)
                                }
                            }
                        }
                        .padding()
                    }
                    
                    if (!products.isEmpty) {
                        LazyVStack {
                            ForEach(products, id: \.id) { product in
                                ProductCardView(
                                    product: product,
                                    onAddToCart: { id in
                                        onAddToCart(product)
                                    },
                                    onRemove: { id in
                                        onRemoveFromCart(product)
                                    },
                                    onShowDetails: { id in
                                        onProductClicked(id)
                                    }
                                )
                            }
                        }
                        .padding()
                    }
                }
                
            }
            .background(Color(.systemGroupedBackground))
        }
    }
}
