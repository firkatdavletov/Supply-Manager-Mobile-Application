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
    let onShowDetails: (Int64) -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            RemoteImage(urlString: product.imageUrl)
                .frame(height: 112)
                .clipShape(
                    RoundedRectangle(cornerRadius: 16, style: .continuous)
                )

            Text("\(product.price.asCurrency())")
                .font(.system(size: 16, weight: .bold, design: .rounded))
                .foregroundColor(Color.onBackground)
                .padding(.horizontal, 8)

            Text(product.title)
                .font(AppTypography.bodyMedium)
                .foregroundColor(Color.onBackground)
                .padding(.horizontal, 8)

            CartButton(
                quantity: Int(product.count),
                onAdd: { onAddToCart(product.id) },
                onRemove: { onRemove(product.id) },
                foregroundColor: Color.onPrimaryContainer
            )
                .padding(.horizontal, 8)
                .padding(.bottom, 8)
                .allowsHitTesting(true)
        }
        .frame(maxWidth: .infinity)
        .background(.white)
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(Color.gray.opacity(0.4), lineWidth: 1)
        )
        .cornerRadius(16)
        .shadow(color: .black.opacity(0.05), radius: 4, x: 0, y: 2)
    }
}