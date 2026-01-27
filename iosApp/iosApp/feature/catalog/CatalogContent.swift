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
    let products: [Shared.ProductModel]
    let amount: Int32
    let productsPrice: Int32
    let freeDeliveryPrice: Int32?
    let onAddToCart: (Shared.ProductModel) -> Void
    let onRemove: (Shared.ProductModel) -> Void
    let onCartButtonClicked: () -> Void
    let onBackButtonClicked: () -> Void
    let onProductCardClicked: (Shared.ProductModel) -> Void
    
    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]
    
    init(
        title: String,
        products: [Shared.ProductModel],
        amount: Int32,
        productsPrice: Int32,
        freeDeliveryPrice: Int32?,
        onAddToCart: @escaping (Shared.ProductModel) -> Void,
        onRemove: @escaping (Shared.ProductModel) -> Void,
        onCartButtonClicked: @escaping () -> Void,
        onBackButtonClicked: @escaping () -> Void,
        onProductCardClicked: @escaping (Shared.ProductModel) -> Void
    ) {
        self.title = title
        self.products = products
        self.amount = amount
        self.productsPrice = productsPrice
        self.freeDeliveryPrice = freeDeliveryPrice
        self.onAddToCart = onAddToCart
        self.onRemove = onRemove
        self.onCartButtonClicked = onCartButtonClicked
        self.onBackButtonClicked = onBackButtonClicked
        self.onProductCardClicked = onProductCardClicked
    }
    
    var body: some View {
        VStack {
            HStack {
                Button(action: onBackButtonClicked) {
                    Image(systemName: "chevron.left")
                        .foregroundColor(Color.onPrimaryContainer)
                }
                Spacer()
                Text(title)
                    .font(AppTypography.titleLarge)
                    .bold()
                    .foregroundColor(Color.onPrimaryContainer)
                Spacer()
            }
            .padding()
            .background(Color.primaryContainer)
            
            ScrollView {
                LazyVGrid(columns: columns, spacing: 16) {
                    ForEach(products, id: \.id) { product in
                        ProductCardView(product: product) { id  in
                            onAddToCart(product)
                        } onRemove: { id in
                            onRemove(product)
                        } onShowDetails: { id in
                            onProductCardClicked(product)
                        }
                    }
                }
                .padding(.horizontal)
            }
            if amount > 0 {
                VStack(spacing: 8) {

                    if freeDeliveryPrice != nil && productsPrice < freeDeliveryPrice! {
                        let remaining = freeDeliveryPrice! - productsPrice
                        let progress = Double(productsPrice) / Double(freeDeliveryPrice!)

                        VStack(alignment: .leading, spacing: 4) {
                            Text("Добавьте ещё \(remaining) ₽ для бесплатной доставки")
                                .font(AppTypography.bodyMedium)
                                .foregroundColor(.primaryContainer)
                                    ProgressView(value: progress)
                                        .progressViewStyle(.linear)
                                        .tint(Color.primaryContainer)
                                }
                                .padding(.horizontal)
                    }

                    PrimaryButton(
                        title: "\(amount) руб",
                        onClick: onCartButtonClicked,
                        enabled: true
                    )
                    .padding(.horizontal)
                }
                .padding(.bottom)
            }
        }
        .navigationBarBackButtonHidden(true)
    }
}

#Preview {
    CatalogContent(
        title: "Pizza",
        products: [
            Shared.ProductModel(
                id: 0,
                title: "Pizza",
                description: nil,
                price: 530.0,
                imageUrl: nil,
                categoryId: 4,
                count: 0
            ),
            Shared.ProductModel(
                id: 1,
                title: "Pizza",
                description: nil,
                price: 530.0,
                imageUrl: nil,
                categoryId: 4,
                count: 5
            ),
            Shared.ProductModel(
                id: 2,
                title: "Pizza",
                description: nil,
                price: 530.0,
                imageUrl: nil,
                categoryId: 4,
                count: 0
            )
        ],
        amount: 300,
        productsPrice: 0,
        freeDeliveryPrice: 0) { ProductModel in
            
        } onRemove: { ProductModel in
            
        } onCartButtonClicked: {
            
        } onBackButtonClicked: {
            
        } onProductCardClicked: { ProductModel in

    }
}
