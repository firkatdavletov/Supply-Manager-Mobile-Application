//
//  CartItemView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 16/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct CartItemView: View {
    let item: CartItemModel
    let onAdd: (CartItemModel) -> Void
    let onRemove: (CartItemModel) -> Void

    var body: some View {
        HStack(alignment: .bottom, spacing: 20) {
            VStack(alignment: .leading) {
                Text(item.title)
                    .font(AppTypography.bodyLarge)
                    .foregroundColor(.onBackground)
                Text("\(Int(item.price)) руб")
                    .font(AppTypography.titleLarge)
                    .foregroundColor(.onBackground)
            }
            Spacer()
            CartButton(
                quantity: Int(item.quantity),
                onAdd: {
                    onAdd(item)
                },
                onRemove: {
                    onRemove(item)
                },
                foregroundColor: Color(.white)
            )
            .frame(width: 120)
        }
        .padding(.horizontal, 16)
    }
}

#Preview {
    CartItemView(
        item: CartItemModel(
            productId: 0,
            title: "Ролл запеченный",
            quantity: 2,
            price: 900
        )
    ) { CartItemModel in
            
        } onRemove: { CartItemModel in
            
        }

}
