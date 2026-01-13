//
//  AiSearchContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 21/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct HomeContent2: View {
    let searchInput: String = ""
    let placeholder: String = "Посоветую, что выбрать"
    let isError: Bool = false
    let address: String = "ул. Щербакова 150/2, кв. 88"
    let onChange: (String) -> Void = { _ in }
    
    var body: some View {
        VStack {
            profileView
            addressView
            ScrollView {
                VStack {
                    searchInputView
                        .padding(.vertical, 8)
                    categoryView
                }
            }
        }
        .padding(.horizontal, 16)
    }
}

extension HomeContent2 {
    private var searchInputView: some View {
        StyledTextField(
            value: searchInput,
            placeholder: placeholder,
            isError: isError,
            onChange: onChange
        )
    }
}

extension HomeContent2 {
    private var profileView: some View {
        HStack {
            Image(systemName: "person")
            Text("+7 (906)-100-37-00")
            Spacer()
        }
    }
}

extension HomeContent2 {
    private var addressView: some View {
        VStack {
            Text("Доставить:")
                .font(AppTypography.headlineSmall)
                .padding(.top, 16)
            HStack {
                Text(address)
                Spacer()
                Button(
                    action: {},
                    label: {
                        Text("Изменить")
                            .padding(.horizontal, 12)
                            .padding(.vertical, 8)
                            .background(Color.surface)
                            .clipShape(RoundedCorner(radius: 12))
                    }
                )
            }
            .font(AppTypography.titleMedium)
            .foregroundStyle(Color.onBackground)
            .padding(.horizontal, 16)
        }
    }
}

extension HomeContent2 {
    private var logoView: some View {
        RemoteImage(
            urlString: "",
        )
        .frame(maxWidth: .infinity, maxHeight: 150)
    }
}

extension HomeContent2 {
    private var categoryView: some View {
        LazyVGrid(
            columns: [
                GridItem(.flexible()),
                GridItem(.flexible())
            ]
        ) {
            ForEach(categories, id: \.id) { category in
                CategoryCardView(title: category.title, imageUrl: category.imageUrl)
                    .frame(height: 150)
            }
        }
    }
}

extension HomeContent2 {
    private var productsView: some View {
        VStack {
            Text("Пицца")
            LazyHStack {
                ForEach (products, id: \.id) { product in
                    ProductCardView(
                        product: product) { Int64 in
                            
                        } onRemove: { Int64 in
                            
                        } onShowDetails: { Int64 in
                            
                        }

                }
            }
        }
    }
}

#Preview {
    HomeContent2()
}

let categories = [
    CategoryModel(
        id: 0,
        title: "Выбор пользователей",
        imageUrl: nil,
        parentCategoryId: 0,
        products: [],
        selected: true,
        span: 3
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
]

let products = [
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
]
