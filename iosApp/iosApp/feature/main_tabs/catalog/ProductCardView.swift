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
    let onIncrease: (Int64) -> Void
    let onDecrease: (Int64) -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            RemoteImage(
                urlString: product.imageUrl ?? "",
                height: 84,
                cornerRadius: 15
            )
                .padding(.bottom, 8)
            
            Text(product.title)
                .font(.system(size: 15, weight: .bold, design: .rounded))
                .foregroundColor(Color("BlackColor"))
                .padding(.bottom, 4)
            
            Text(product.description_ ?? "")
                .font(.system(size: 13, weight: .regular, design: .rounded))
                .foregroundColor(Color("BlackColor"))
                .lineLimit(2)
            Spacer()
            HStack {
                Text("\(Int(product.price))₽")
                    .font(.system(size: 16, weight: .bold, design: .rounded))
                    .foregroundColor(Color("BlackColor"))
                
                Spacer()
            }
            HStack {
                Spacer()
                
                CartButton(
                    quantity: Binding(
                        get: { Int(product.count) },
                        set: { _ in }
                    ),
                    onAdd: { onAddToCart(product.id) },
                    onIncrease: { onIncrease(product.id) },
                    onDecrease: { onDecrease(product.id) },
                    foregroundColor: Color("BlackColor")
                )
            }
        }
        .padding(8)
        .frame(height: 240)
        .background(Color(.white))
        .clipShape(RoundedCorner(radius: 24))
        .shadow(color: Color("LightGrey"), radius: 4)
    }
}

struct CartButton: View {
    @Binding var quantity: Int
    let onAdd: () -> Void
    let onIncrease: () -> Void
    let onDecrease: () -> Void
    let foregroundColor: Color
    
    var body: some View {
        HStack(spacing: 8) {
            if quantity == 0 {
                Button(action: {
                    onAdd()
                }) {
                    Image(systemName: "plus")
                        .foregroundColor(.white)
                        .frame(width: 30, height: 30)
                        .background(Color("PrimaryColor"))
                        .clipShape(Circle())
                }
            } else {
                Button(action: {
                    if quantity > 0 {
                        onDecrease()
                    }
                }) {
                    Image(systemName: "minus")
                        .foregroundColor(.white)
                        .frame(width: 30, height: 30)
                        .background(Color("PrimaryColor"))
                        .clipShape(Circle())
                }

                Text("\(quantity)")
                    .font(.system(size: 16, weight: .medium))
                    .frame(minWidth: 20)
                    .foregroundColor(foregroundColor)

                Button(action: {
                    onIncrease()
                }) {
                    Image(systemName: "plus")
                        .foregroundColor(.white)
                        .frame(width: 30, height: 30)
                        .background(Color("PrimaryColor"))
                        .clipShape(Circle())
                }
            }
        }
    }
}
