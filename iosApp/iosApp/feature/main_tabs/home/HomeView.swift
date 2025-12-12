//
//  HomeView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//P

import SwiftUI
import Shared

struct HomeView: View {
    let component: HomeComponent
    
    @StateValue private var state: HomeViewState
    
    init(component: HomeComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    
    var body: some View {
        VStack(spacing: 12) {
            HStack(spacing: 12) {
                VStack(alignment: .leading, spacing: 4) {
                    if (state.deliveryType == DeliveryType.pickup) {
                        Text(state.cartDepartment!.name)
                            .font(.system(size: 16, weight: .bold, design: .rounded))
                            .foregroundColor(Color("PrimaryColor"))
                        HStack(alignment: .top,spacing: 8) {
                            Text("Самовывоз")
                                .font(.system(size: 16, weight: .regular, design: .rounded))
                                .foregroundColor(Color("DarkGrayColor"))
                        }
                    } else {
                        Text("\(state.deliveryAddress)")
                            .font(.system(size: 16, weight: .bold, design: .rounded))
                            .foregroundColor(Color("PrimaryColor"))
                        HStack(alignment: .top,spacing: 8) {
                            Text("Доставка")
                                .font(.system(size: 16, weight: .regular, design: .rounded))
                                .foregroundColor(Color("DarkGrayColor"))
                            Text(state.deliveryInfo)
                                .font(.system(size: 16, weight: .regular, design: .rounded))
                                .foregroundColor(Color("DarkGrayColor"))
                        }
                    }
                }
                .frame(alignment: .leading)
                .onTapGesture {
                    component.onEvent(event: HomeViewEventOnAddressClicked())
                }
                Spacer()
                Button(action: {
                    
                }) {
                    Image(systemName: "text.alignright")
                        .foregroundColor(.white) // цвет иконки
                        .frame(width: 36, height: 36)
                        .background(Circle().fill(Color("DarkGrayColor"))) // чёрный круглый фон
                }
                .buttonStyle(PlainButtonStyle())
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding()
            
            CategoryListView(
                categories: state.categories,
                orders: state.currentOrders,
                onClick: { category in
                    component.onEvent(event: HomeViewEventOnCategoryClicked(
                        categoryId: category.id,
                        categoryTitle: category.title
                    ))
                }
            )
            
            if state.amount > 0 {
                HStack {
                    Text("\(String(format: "%.2f", state.amount)) ₽")
                        .font(.headline)
                        .foregroundColor(.white)
                    Spacer()
                    Button(action: {
                        component.onEvent(event: HomeViewEventOnCartButtonClicked())
                    }) {
                        Text("Перейти в корзину")
                            .font(.headline)
                            .foregroundColor(.white)
                    }
                }
                .padding()
                .background(Color("PrimaryColor"))
                .cornerRadius(25)
                .padding([.horizontal, .bottom, .top], 16)
                .background(Color("LightGrey"))
                .transition(.move(edge: .bottom).combined(with: .opacity))
                .animation(.easeInOut, value: state.amount)
            }
        }
        .navigationBarBackButtonHidden(true)
    }
}

struct CategoryListView: View {
    let categories: [CategoryModel]
    let orders: [OrderModel]
    let onClick: (CategoryModel) -> Void
    private let columns = [
        GridItem(.flexible(), alignment: .top),
        GridItem(.flexible(), alignment: .top)
        ]
    var body: some View {
        ScrollViewReader { proxy in
            ScrollView {
                HStack(spacing: 12) {
                    Image(systemName: "magnifyingglass")
                        .resizable()
                        .frame(width: 16, height: 16)
                        .foregroundColor(Color("SlateBlueGray"))
                    Text("Поиск")
                        .font(.system(size: 14, weight: .regular, design: .rounded))
                        .foregroundColor(Color("DarkGrayColor"))
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(22)
                .background(Color("IceBlue"))
                .cornerRadius(25)
                .padding(.horizontal)
                
                LazyVStack {
                    ForEach(orders, id: \.id) { order in
                        OrderCell(order: order)
                    }
                }
                
                LazyVGrid(columns: columns, spacing: 8) {
                    ForEach(categories, id: \.id) { category in
                        CategoryCell(category: category)
                            .onTapGesture {
                                onClick(category)
                            }
                    }
                }
                .padding(.horizontal, 16)
            }
        }
    }
}


struct CategoryCell: View {
    let category: CategoryModel

    var body: some View {
        CategoryCardView(title: category.title, imageUrl: category.imageUrl)
    }
}

struct OrderCell: View {
    let order: OrderModel
    
    var body: some View {
        HStack(alignment: .top, spacing: 8) {
            Text("Заказ №\(order.id)")
                .font(.system(size: 15, weight: .bold, design: .rounded))
                .foregroundColor(Color("DarkGrayColor"))
                .padding(.horizontal, 20)
            Spacer()
            Text("\(order.status.name)")
                .font(.system(size: 15, weight: .bold, design: .rounded))
                .foregroundColor(Color("DarkGrayColor"))
                .padding(.horizontal, 20)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(16)
        .background(Color("IceBlue"))
        .cornerRadius(25)
        .padding(.horizontal)
    }
}
