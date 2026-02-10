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
            topBar
            content
        }
        .background(Color(.systemGroupedBackground))
        .safeAreaInset(edge: .bottom) {
            if shouldShowCartButton {
                cartButton
            }
        }
    }
}

private extension CatalogContent {
    var shouldShowCartButton: Bool {
        amount != 0
    }
    
    var topBar: some View {
        CatalogTopBar(
            title: title,
            onBack: onBackButtonClicked
        )
    }
    
    var content: some View {
        ScrollView {
            VStack(spacing: 16) {
                if isLoading {
                    ProgressView()
                }
                
                categoriesSection
                productsSection
            }
            .padding()
        }
        .background(Color(.systemGroupedBackground))
    }
    
    var categoriesSection: some View {
        Group {
            if !categories.isEmpty {
                LazyVStack(spacing: 12) {
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
            }
        }
    }
    
    var productsSection: some View {
        Group {
            if !products.isEmpty {
                LazyVStack(spacing: 12) {
                    ForEach(products, id: \.id) { product in
                        ProductCardView(
                            product: product,
                            onAddToCart: { _ in
                                onAddToCart(product)
                            },
                            onRemove: { _ in
                                onRemoveFromCart(product)
                            },
                            onShowDetails: { id in
                                onProductClicked(id)
                            }
                        )
                    }
                }
            }
        }
    }
    
    var cartButton: some View {
        PrimaryButton(
            title: "Перейти в корзину (\(amount.asCurrency()))",
            onClick: onCartButtonClicked,
            enabled: !isLoading
        )
        .padding(.horizontal, 16)
        .padding(.top, 8)
        .padding(.bottom, 12)
        .background(Color.background)
    }
}
