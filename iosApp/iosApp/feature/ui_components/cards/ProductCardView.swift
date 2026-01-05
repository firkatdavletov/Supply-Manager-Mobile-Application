//
//  ProductCardView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 10.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct ProductCardView: View {
    let product: Shared.ProductModel
    let onAddToCart: (Int64) -> Void
    let onRemove: (Int64) -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            RemoteImage(urlString: product.imageUrl)
                .frame(height: 112)
                .clipShape(
                    RoundedRectangle(cornerRadius: 16, style: .continuous)
                )

            Text("\(Int(product.price)) руб")
                .font(.system(size: 16, weight: .bold, design: .rounded))
                .foregroundColor(Color.onBackground)
                .padding(.horizontal, 8)

            Text(product.title)
                .font(AppTypography.bodyMedium)
                .foregroundColor(Color.onBackground)
                .padding(.horizontal, 8)

            Spacer()

            CartButton(
                quantity: Int(product.count),
                onAdd: { onAddToCart(product.id) },
                onRemove: { onRemove(product.id) },
                foregroundColor: Color.onPrimaryContainer
            )
                .padding(.horizontal, 8)
                .padding(.bottom, 8)
        }
        .background(Color.surface)
        .clipShape(RoundedRectangle(cornerRadius: 16))
        .frame(height: 234)
    }
}

#Preview {
    ProductCardView(
        product: Shared.ProductModel(
            id: 0,
            title: "Pizza",
            description: nil,
            price: 530.0,
            imageUrl: nil,
            categoryId: 4,
            count: 56
        )) { Int64 in
            
        } onRemove: { Int64 in
            
        }
        .padding()

}
