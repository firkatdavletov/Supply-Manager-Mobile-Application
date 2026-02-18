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
    let companyName: String?
    let createdAt: String
    let orderStatus: OrderStatus
    let customerName: String
    let customerEmail: String
    let customerPhone: String
    let deliveryType: DeliveryType
    let address: String
    let comment: String
    let orderItems: [OrderItemModel]
    let totalAmount: Int64
    let deliveryDate: String
    let onBackButtonClicked: () -> Void
    let onTakeOrder: () -> Void
    let onCompleteOrder: () -> Void
    let onCancelOrder: () -> Void
    let onPendingOrder: () -> Void

    let statuses: [OrderStatus] = [
        .completed,
        .processing,
        .pending,
        .cancelled
    ]

    var body: some View {
        VStack(spacing: 0) {
            OrderTopBar(onBack: onBackButtonClicked)

            ScrollView {
                orderTitle()
                contactInfo()
                orderDetails()
                commentSection()
                orderItemsView()

                if (orderStatus != .cancelled && orderStatus != .completed) {
                    updateStatus()
                }
            }
            .background(Color(.systemGroupedBackground))
        }
    }

    @ViewBuilder
    private func orderTitle() -> some View {
        HStack(alignment: .top, spacing: 8) {
            VStack(alignment: .leading, spacing: 12) {

                if (companyName != nil) {
                    Text(companyName!)
                        .font(AppTypography.titleMedium)
                        .foregroundStyle(Color.onBackground)
                }

                HStack(alignment: .top, spacing: 8) {
                    Image(systemName: "clock")
                        .foregroundColor(.gray)
                    Text(createdAt)
                }
            }
            Spacer()
            StatusChip(status: orderStatus)
        }
        .padding()
        .background(.white)
    }

    @ViewBuilder
    private func contactInfo() -> some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Контактная информация")
                .font(AppTypography.titleMedium)
                .foregroundStyle(Color.onBackground)
            contactInfoTile(image: "person", title: "Имя", content: customerName)
            contactInfoTile(image: "envelope", title: "Электронная почта", content: customerEmail)
            contactInfoTile(image: "phone", title: "Телефонный номер", content: customerPhone)
        }
        .padding(12)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 25))
        .contentShape(RoundedRectangle(cornerRadius: 25))
        .padding(.horizontal)
    }

    @ViewBuilder
    private func contactInfoTile(
        image: String,
        title: String,
        content: String
    ) -> some View {
        HStack {
            Image(systemName: image)
                .font(.system(size: 18, weight: .bold))
                .foregroundColor(.white)
                .frame(width: 40, height: 40)
                .background(Color.gray)
                .clipShape(RoundedCorner(radius: 8))
            VStack(alignment: .leading) {
                Text(title)
                    .font(AppTypography.bodySmall)
                    .foregroundStyle(Color.gray)
                Text(content)
                    .font(AppTypography.titleMedium)
                    .foregroundStyle(Color.onBackground)
            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }

    @ViewBuilder
    private func orderDetails() -> some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Детали заказа")
                .font(AppTypography.titleMedium)
                .foregroundStyle(Color.onBackground)
            contactInfoTile(image: "number", title: "Количество единиц", content: "\(orderItems.count)")
            contactInfoTile(image: "rublesign", title: "Сумма заказа", content: "\(totalAmount.asCurrency())")
            contactInfoTile(image: "calendar", title: "Дата поставки", content: deliveryDate)
        }
        .padding(12)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 25))
        .contentShape(RoundedRectangle(cornerRadius: 25))
        .padding(.horizontal)
    }

    @ViewBuilder
    private func commentSection() -> some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack {
                Image(systemName: "captions.bubble")
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(.gray)
                Text("Комментарий к заказу")
                    .font(AppTypography.titleMedium)
                    .foregroundStyle(Color.onBackground)
            }
            Text(comment)
                .font(AppTypography.bodySmall)
                .foregroundStyle(Color.onBackground)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(12)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 25))
        .contentShape(RoundedRectangle(cornerRadius: 25))
        .padding(.horizontal)
    }

    @ViewBuilder
    private func updateStatus() -> some View {
        let columns: [GridItem] = [
            GridItem(.flexible(minimum: 0)),
            GridItem(.flexible(minimum: 0))
        ]

        VStack(alignment: .leading, spacing: 16) {
            Text("Обновление статуса")
                .font(AppTypography.titleMedium)
                .foregroundStyle(Color.onBackground)

            LazyVGrid(columns: columns) {
                Text("В ожидании")
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(orderStatus == OrderStatus.pending ? .blue : .onBackground)
                    .padding(12)
                    .frame(maxWidth: .infinity, alignment: .init(horizontal: .center, vertical: .center))
                    .overlay(
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(orderStatus == OrderStatus.pending ? .blue : .onBackground, lineWidth: 1)
                    )
                    .cornerRadius(8)
                    .onTapGesture(perform: onPendingOrder)
                Text("Отправить")
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(.white)
                    .padding(12)
                    .frame(maxWidth: .infinity, alignment: .init(horizontal: .center, vertical: .center))
                    .background(.green)
                    .cornerRadius(8)
                    .onTapGesture(perform: onCompleteOrder)
                Text("Проверка")
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(orderStatus == OrderStatus.processing ? .blue : .onBackground)
                    .padding(12)
                    .frame(maxWidth: .infinity, alignment: .init(horizontal: .center, vertical: .center))
                    .overlay(
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(orderStatus == OrderStatus.processing ? .blue : .onBackground, lineWidth: 1)
                    )
                    .cornerRadius(8)
                    .onTapGesture(perform: onTakeOrder)
                Text("Отменить")
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(.white)
                    .padding(12)
                    .frame(maxWidth: .infinity, alignment: .init(horizontal: .center, vertical: .center))
                    .background(.red)
                    .cornerRadius(8)
                    .onTapGesture(perform: onCancelOrder)
            }

        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(12)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 25))
        .contentShape(RoundedRectangle(cornerRadius: 25))
        .padding(.horizontal)
    }

    @ViewBuilder
    private func orderItemsView() -> some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack {
                Image(systemName: "bag")
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(.gray)
                Text("Товары в заказе")
                    .font(AppTypography.titleMedium)
                    .foregroundStyle(Color.onBackground)
            }
            LazyVStack(spacing: 12) {
                ForEach(orderItems, id: \.productId) { orderItem in
                    orderItemView(orderItem: orderItem)
                }
            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(12)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 25))
        .contentShape(RoundedRectangle(cornerRadius: 25))
        .padding(.horizontal)
    }

    @ViewBuilder
    private func orderItemView(orderItem: OrderItemModel) -> some View {
        HStack(alignment: .top) {
            Image(systemName: "shippingbox")
                .font(.system(size: 18, weight: .bold))
                .foregroundColor(.white)
                .frame(width: 40, height: 40)
                .background(Color.gray)
                .clipShape(RoundedCorner(radius: 8))
            VStack(alignment: .leading) {
                Text(orderItem.name)
                    .font(AppTypography.titleMedium)
                    .foregroundStyle(Color.onBackground)
                Text("\(orderItem.price.asCurrency())")
                    .font(AppTypography.bodySmall)
                    .foregroundStyle(Color.gray)
            }
            Spacer()
            VStack(alignment: .trailing) {
                Text("\(orderItem.quantity) шт.")
                    .font(AppTypography.titleMedium)
                    .foregroundStyle(Color.onBackground)
                Text("\(orderItem.totalPrice.asCurrency())")
                    .font(AppTypography.bodySmall)
                    .foregroundStyle(Color.gray)
            }
        }
    }
}
