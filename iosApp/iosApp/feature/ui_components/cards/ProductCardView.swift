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
        VStack(alignment: .leading, spacing: 0) {
            ZStack(alignment: .bottomTrailing) {
                RemoteImage(
                    urlString: product.imageUrl ?? "",
                    cornerRadius: 16
                )
                .frame(maxWidth: .infinity)
                
                CartButton(
                    quantity: Int(product.count),
                    onAdd: { onAddToCart(product.id) },
                    onRemove: { onRemove(product.id) },
                    foregroundColor: Color("BlackColor")
                )
                .padding(8)
                .frame(maxWidth: .infinity)
            }
            .frame(height: 150)
            .padding(.bottom, 8)
            
            Text("\(Int(product.price)) руб")
                .font(.system(size: 16, weight: .bold, design: .rounded))
                .foregroundColor(Color.onBackground)
            
            Text(product.title)
                .font(AppTypography.bodyMedium)
                .foregroundColor(Color.onBackground)
                .padding(.bottom, 4)
            Spacer()
            
        }
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
