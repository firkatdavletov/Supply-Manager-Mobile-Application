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
    let onAddToCart: (Shared.ProductModel) -> Void
    let onRemove: (Shared.ProductModel) -> Void
    let onCartButtonClicked: () -> Void
    let onBackButtonClicked: () -> Void
    
    let columns = [
            GridItem(.flexible(), spacing: 16),
            GridItem(.flexible(), spacing: 16)
        ]
    
    init(
        title: String,
        products: [Shared.ProductModel],
        amount: Int32,
        onAddToCart: @escaping (Shared.ProductModel) -> Void,
        onRemove: @escaping (Shared.ProductModel) -> Void,
        onCartButtonClicked: @escaping () -> Void,
        onBackButtonClicked: @escaping () -> Void
    ) {
        self.title = title
        self.products = products
        self.amount = amount
        self.onAddToCart = onAddToCart
        self.onRemove = onRemove
        self.onCartButtonClicked = onCartButtonClicked
        self.onBackButtonClicked = onBackButtonClicked
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
                        ProductCardView(
                            product: product,
                            onAddToCart: { id in
                                onAddToCart(product)
                            },
                            onRemove: { id in
                                onRemove(product)
                            }
                        )
                    }
                }
                .padding()
            }
            if amount > 0 {
                RoundedButton(
                    title: "\(amount) руб",
                    onClick: onCartButtonClicked,
                    background: Color.primaryContainer,
                    foreground: Color.onPrimaryContainer,
                    enabled: true
                )
                .padding(.horizontal)
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
        amount: 300) { ProductModel in
            
        } onRemove: { ProductModel in
            
        } onCartButtonClicked: {
            
        } onBackButtonClicked: {
            
        }

}
