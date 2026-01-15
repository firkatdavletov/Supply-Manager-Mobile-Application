//
//  CurrentOrderContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 19/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct CurrentOrderContent: View {
    let title: String
    let statusTitle: String
    let deliveryType: DeliveryType
    let address: String
    let comment: String
    let orderItems: [OrderItemModel]
    let productPrice: Int
    let deliveryPrice: Int
    let totalPrice: Int
    let onBackButtonClicked: () -> Void
    
    var body: some View {
        VStack {
            HStack {
                Button(action: onBackButtonClicked) {
                    Image(systemName: "chevron.left")
                        .foregroundColor(Color.onPrimaryContainer)
                }
                Spacer()
                Text("Заказ №\(title)")
                    .font(AppTypography.titleLarge)
                    .bold()
                    .foregroundColor(Color.onPrimaryContainer)
                Spacer()
            }
            .padding()
            .background(Color.primaryContainer)
        }
        ScrollView {
            orderStatus
            Divider()
            deliveryInfo
            Divider()
            if (!comment.isEmpty) {
                commentView
                Divider()
            }
            orderItemsView
            Divider()
        }
        paymentInfo
    }
}

extension CurrentOrderContent {
    var orderStatus: some View {
        VStack(spacing: 16) {
            Text("Статус заказа")
                .font(AppTypography.titleLarge)
                .foregroundColor(Color.onBackground)
            Text(statusTitle)
                .font(AppTypography.headlineSmall)
                .foregroundColor(Color.onBackground)
        }
        .padding(.horizontal)
    }
}

extension CurrentOrderContent {
    var deliveryInfo: some View {
        VStack(spacing: 16) {
            if (deliveryType == DeliveryType.delivery) {
                HStack {
                    Text("Адрес доставки:")
                        .font(AppTypography.titleLarge)
                        .foregroundColor(Color.onBackground)
                    Spacer()
                }
            } else {
                HStack {
                    Text("Адрес самовывоза:")
                        .font(AppTypography.titleLarge)
                        .foregroundColor(Color.onBackground)
                    Spacer()
                }
            }
            
            HStack {
                Text(address)
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(Color.onBackground)
                Spacer()
            }
        }
        .padding(.horizontal)
    }
}

extension CurrentOrderContent {
    var commentView: some View {
        HStack {
            Text("Комментарий: \(comment)")
            Spacer()
        }
        .padding()
    }
}

extension CurrentOrderContent {
    var orderItemsView: some View {
        LazyVStack(spacing: 16) {
            ForEach(orderItems, id: \.productId) { item in
                OrderItemView(orderItem: item)
                    .padding(.horizontal)
            }
        }
    }
}

extension CurrentOrderContent {
    var paymentInfo: some View {
        VStack(spacing: 16) {
            HStack {
                Text("Товары:")
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(Color.onBackground)
                Spacer()
                Text("\(productPrice) руб")
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(Color.onBackground)
            }
            if (deliveryType == DeliveryType.delivery) {
                HStack {
                    Text("Доставка:")
                        .font(AppTypography.bodyLarge)
                        .foregroundStyle(Color.onBackground)
                    Spacer()
                    Text("\(deliveryPrice) руб")
                        .font(AppTypography.bodyLarge)
                        .foregroundStyle(Color.onBackground)
                }
            }
            HStack {
                Text("Итого:")
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(Color.onBackground)
                Spacer()
                Text("\(totalPrice) руб")
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(Color.onBackground)
            }
        }
        .padding()
    }
}

#Preview {
    CurrentOrderContent(
        title: "Заказ № 1234",
        statusTitle: "Готовим",
        deliveryType: DeliveryType.delivery,
        address: "ул. Косоротова, 150",
        comment: "Оставить у подъезда",
        orderItems: [
            OrderItemModel(
                productId: 0,
                name: "Ролл",
                quantity: 2,
                price: 399
            ),
            OrderItemModel(
                productId: 2,
                name: "Ролл",
                quantity: 2,
                price: 399
            )
        ],
        productPrice: 798,
        deliveryPrice: 100,
        totalPrice: 898) {
            
        }
}
