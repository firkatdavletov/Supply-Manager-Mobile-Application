//
//  HomeContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 12/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//


import SwiftUI
import Shared

struct HomeContent: View {
    let userName: String?
    let addressString: String
    let deliveryInfo: String
    let currentOrders: [OrderUIModel]
    let categories: [CategoryModel]
    let totalAmount: Float
    let onChangeAddressClicked: () -> Void
    let onCategoryClicked: (CategoryModel) -> Void
    let onCartButtonClicked: () -> Void
    let onPersonClicked: () -> Void
    let onOrderClicked: (Int64) -> Void

    @State private var focused: Bool = false
    
    var columns = [
      GridItem(.flexible()),
      GridItem(.flexible()),
    ]

    var body: some View {
        VStack {
            headerView
            
            ScrollView {
                LazyVGrid(
                    columns: columns,
                ) {
                    Section(header: ordersPagerItem) {
                        
                    }
                    ForEach(categories, id: \.id) { category in
                        CategoryCardView(title: category.title, imageUrl: category.imageUrl)
                            .onTapGesture {
                                onCategoryClicked(category)
                            }
                    }
                }
                .padding(.horizontal)
            }

            Spacer()
            
            if totalAmount > 0 {
                RoundedButton(
                    title: "\(Int(totalAmount)) руб",
                    onClick: {
                        onCartButtonClicked()
                    },
                    background: Color.primaryContainer,
                    foreground: Color.onPrimaryContainer,
                    enabled: true
                )
                .padding(.horizontal)
            }
        }
        .onTapGesture {
            focused = false
        }
        .background(.background)
    }
}

extension HomeContent {
    private var headerView: some View {
        HStack(alignment: .top,spacing: 8) {
            VStack(alignment: .leading, spacing: 8) {

                Text(addressString)
                    .font(AppTypography.titleLarge)
                    .foregroundColor(.onPrimaryContainer)

                HStack(spacing: 16) {
                    Text(deliveryInfo)
                        .font(AppTypography.bodyMedium)
                        .foregroundColor(.onPrimaryContainer)

                    Text("Изменить")
                        .font(AppTypography.bodySmall)
                        .padding(.vertical, 4)
                        .padding(.horizontal, 6)
                        .background(Color.primaryContainer)
                        .foregroundColor(Color.onPrimaryContainer)
                        .cornerRadius(12)
                        .padding(1)
                        .background(Color.onPrimaryContainer)
                        .cornerRadius(12)
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .onTapGesture { onChangeAddressClicked() }

            Button(action: onPersonClicked) {
                Image(systemName: "person.circle")
                    .font(.title2)
                    .foregroundStyle(Color.onPrimaryContainer)
            }
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 16)
        .background(Color.primaryContainer)
    }
}

extension HomeContent {
    @ViewBuilder
    private var greetingItem: some View {
        if let name = userName {
            Text("Привет, \(name)!")
                .font(AppTypography.headlineSmall)
                .frame(maxWidth: .infinity, alignment: .leading)
        }
    }
}

extension HomeContent {
    private var ordersPagerItem: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHStack {
                ForEach(currentOrders, id: \.self) { order in
                    HomeOrderView(
                        orderNumber: order.number,
                        status: order.status,
                        amount: Int(order.amount)
                    )
                    .padding(.vertical, 2)
                    .frame(width: UIScreen.main.bounds.width - 32)
                    .onTapGesture {
                        onOrderClicked(order.id)
                    }
                }
            }
        }
    }
}

extension HomeContent {
    private func safeAreaBottom() -> CGFloat {
        let window = UIApplication.shared.connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .flatMap { $0.windows }
            .first { $0.isKeyWindow }

        return window?.safeAreaInsets.bottom ?? 0
    }

    private func safeAreaTop() -> CGFloat {
        let window = UIApplication.shared.connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .flatMap { $0.windows }
            .first { $0.isKeyWindow }

        return window?.safeAreaInsets.bottom ?? 0
    }
}

struct HomeOrderView: View {
    let orderNumber: String
    let status: String
    let amount: Int

    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 8) {
                Text("Заказ №\(orderNumber)")
                    .font(AppTypography.bodyMedium)
                    .foregroundStyle(Color.onSecondaryContainer)
                Text(status)
                    .font(AppTypography.titleMedium)
                    .foregroundStyle(Color.onSecondaryContainer)
            }
            Spacer()
            Text("\(amount) руб")
                .font(AppTypography.titleLarge)
                .foregroundStyle(Color.onSecondaryContainer)
        }
        .padding(12)
        .frame(maxWidth: .infinity)
        .background(Color.background)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(Color.primaryContainer, lineWidth: 0)
        )
    }
}

struct HomeCategoryView: View {
    let title: String
    let imageUrl: String?

    var body: some View {
        VStack {
            Rectangle()
                .fill(Color.gray.opacity(0.2))
                .aspectRatio(1, contentMode: .fit)
            Text(title)
                .font(.footnote)
        }
        .background(Color(.systemBackground))
        .cornerRadius(12)
    }
}

#Preview {
    HomeContent(
        userName: "Firkat",
        addressString: "улица Косоротова, 6",
        deliveryInfo: "Доставка 100 ₽",
        currentOrders: [
            OrderUIModel(
                id: 1,
                number: "1243",
                status: "Собираем",
                amount: 100
            ),
            OrderUIModel(
                id: 2,
                number: "467",
                status: "В пути",
                amount: 100
            )
        ],
        categories: [
            CategoryModel(
                id: 0,
                title: "Выбор пользователей",
                imageUrl: nil,
                parentCategoryId: 0,
                products: [],
                selected: true,
                span: 2
            ),
            CategoryModel(
                id: 1,
                title: "Избранное",
                imageUrl: nil,
                parentCategoryId: 0,
                products: [],
                selected: true,
                span: 2
            ),
            CategoryModel(
                id: 2,
                title: "Пицца",
                imageUrl: nil,
                parentCategoryId: 0,
                products: [],
                selected: true,
                span: 2
            ),
            CategoryModel(
                id: 3,
                title: "Выбор пользователей",
                imageUrl: nil,
                parentCategoryId: 0,
                products: [],
                selected: true,
                span: 2
            ),
            CategoryModel(
                id: 4,
                title: "Избранное",
                imageUrl: nil,
                parentCategoryId: 0,
                products: [],
                selected: true,
                span: 2
            ),
            CategoryModel(
                id: 5,
                title: "Пицца",
                imageUrl: nil,
                parentCategoryId: 0,
                products: [],
                selected: true,
                span: 2
            )
        ],
        totalAmount: 100.0) {
            
        } onCategoryClicked: { CategoryModel in
            
        } onCartButtonClicked: {
            
        } onPersonClicked: {
            
        } onOrderClicked: { Int64 in
            
        }

}

