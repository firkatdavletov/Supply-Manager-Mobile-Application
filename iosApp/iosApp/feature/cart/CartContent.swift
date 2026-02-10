//
//  CartContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 16/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct CartContent: View {
    let cartItems: [CartItemModel]
    let productPrice: Int64
    let deliveryPrice: Int64
    let totalAmount: Int64
    let freeDeliveryPrice: Int64?
    let deliveryType: DeliveryType
    let onAddToCart: (CartItemModel) -> Void
    let onRemoveFromCart: (CartItemModel) -> Void
    let onBack: () -> Void
    let onConfirm: () -> Void

    var body: some View {
        VStack(spacing: 0) {
            headerView
                .padding(16)
                .background(Color.blue)
            // Content
            cartItemList
            // Bottom info and button
            infoView
                .padding(.horizontal)
        }
    }
}

extension CartContent {
    private var headerView: some View {
        HStack {
            Button(action: onBack) {
                Image(systemName: "chevron.left")
                .foregroundColor(Color.onPrimaryContainer)
            }
            Spacer()
            Text("Корзина")
                .font(AppTypography.titleLarge)
                .bold()
                .foregroundColor(Color.onPrimaryContainer)
            Spacer()
        }
    }
}

extension CartContent {
    private var cartItemList: some View {
        ScrollView {
            LazyVStack(spacing: 12) {
                ForEach(cartItems, id: \.productId) { item in
                    CartItemView(
                        item: item,
                        onAdd: { item in
                            onAddToCart(item)
                        },
                        onRemove: { item in
                            onRemoveFromCart(item)
                        }
                    )
                    Divider()
                }
            }
            .padding()
        }
    }
}

extension CartContent {
    private var infoView: some View {
        VStack(alignment: .leading, spacing: 12) {
            HStack {
                Text("Стоимость продуктов:")
                    .font(AppTypography.bodyLarge)
                    .foregroundColor(Color.onBackground)
                Spacer()

                Text("\(productPrice.asCurrency())")
                    .font(AppTypography.titleLarge)
                    .foregroundColor(Color.onBackground)
            }

            if (deliveryType == DeliveryType.delivery) {
                HStack {
                    Text("Стоимость доставки:")
                        .font(AppTypography.bodyLarge)
                        .foregroundColor(Color.onBackground)
                    Spacer()

                    if (totalAmount == 0 || (freeDeliveryPrice != nil && productPrice > freeDeliveryPrice!)) {
                        Text("бесплатно")
                            .font(AppTypography.titleLarge)
                            .foregroundColor(Color.onBackground)
                    } else {
                        Text("\(deliveryPrice.asCurrency)")
                            .font(AppTypography.titleLarge)
                            .foregroundColor(Color.onBackground)
                    }
                }
            }
            HStack {
                Text("Итого:")
                    .font(AppTypography.bodyLarge)
                    .foregroundColor(Color.onBackground)
                Spacer()
                Text("\(totalAmount.asCurrency()) руб")
                    .font(AppTypography.titleLarge)
                    .foregroundColor(Color.onBackground)
            }
            PrimaryButton(
                title: "Перейти к заказу",
                onClick: onConfirm,
                enabled: !cartItems.isEmpty
            )
            .padding(.vertical)
        }
    }
}

#Preview {
    CartContent(
        cartItems: [
            CartItemModel(
                productId: 0,
                title: "Ролл запеченный",
                quantity: 2,
                price: 900
            )
        ],
        productPrice: 300,
        deliveryPrice: 100,
        totalAmount: 400,
        freeDeliveryPrice: 0,
        deliveryType: DeliveryType.delivery
    ) { CartItemModel in

    } onRemoveFromCart: { CartItemModel in

    } onBack: {

    } onConfirm: {

    }
}
