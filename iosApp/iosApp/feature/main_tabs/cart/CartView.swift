//
//  CartView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 11.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct CartView: View {
    let component: CartComponent
    
    @StateValue private var state: CartViewState
    
    init(component: CartComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    
    var body: some View {
        VStack(spacing: 0) {
            // Content
            ScrollView {
                VStack(spacing: 12) {
                    ForEach(state.cartItems, id: \.productId) { item in
                        CartItemView(
                            item: item,
                            onAdd: { item in
                                component.onEvent(event: CartViewEventOnAddToCart(product: item))
                            },
                            onRemove: { item in
                                component.onEvent(event: CartViewEventOnRemoveFromCart(product: item))
                            }
                        )
                    }
                }
                .padding()
            }
            // Bottom info and button
            VStack(alignment: .leading, spacing: 12) {
                if (!state.addressString.isEmpty) {
                    Text("Адрес:")
                        .font(.system(size: 14, weight: .regular, design: .rounded))
                        .frame(alignment: .leading)
                    HStack {
                        Image(systemName: "magnifyingglass")
                            .resizable()
                            .frame(width: 16, height: 16)
                            .foregroundColor(Color("SlateBlueGray"))
                        
                        Text(state.addressString)
                            .font(.system(size: 16, weight: .regular, design: .rounded))
                        
                        Spacer()
                    }
                    .padding()
                    .frame(maxWidth: .infinity, minHeight: 50)
                    .foregroundColor(Color("DarkGrayColor"))
                    .background(Color("IceBlue"))
                    .cornerRadius(25)
                    .onTapGesture {
                        
                    }
                    HStack {
                        Text("Продукты:")
                            .font(.system(size: 14, weight: .regular, design: .rounded))
                            .foregroundColor(Color("DarkGrayColor"))
                        Text("\(Int(state.totalPrice))₽")
                            .font(.system(size: 14, weight: .bold, design: .rounded))
                            .foregroundColor(Color("BlackColor"))
                    }
                    if (state.deliveryType == .pickup) {
                        HStack {
                            Text("Самовывоз")
                                .font(.system(size: 14, weight: .regular, design: .rounded))
                                .foregroundColor(Color("DarkGrayColor"))
                        }
                    } else {
                        HStack {
                            Text("Доставка")
                                .font(.system(size: 14, weight: .regular, design: .rounded))
                                .foregroundColor(Color("DarkGrayColor"))
                            if (state.deliveryPrice > 0) {
                                Text(": \(Int(state.deliveryPrice))₽")
                                    .font(.system(size: 14, weight: .bold, design: .rounded))
                                    .foregroundColor(Color("BlackColor"))
                            } else {
                                Text("бесплатно")
                                    .font(.system(size: 14, weight: .bold, design: .rounded))
                                    .foregroundColor(Color(.green))
                            }
                        }
                    }
                }
                
                HStack {
                    Text("Итого:")
                        .font(.system(size: 14, weight: .regular, design: .rounded))
                        .foregroundColor(Color("DarkGrayColor"))
        
                    Text("\(Int(state.totalPrice + state.deliveryPrice))₽")
                        .font(.system(size: 30, weight: .bold, design: .rounded))
                        .foregroundColor(Color("BlackColor"))
                }
                ConfirmButton(
                    title: state.continueText,
                    onConfirm: {
                        component.onEvent(event: CartViewEventOnConfirmButtonClicked())
                    },
                    isLoading: false,
                    isDisabled: false
                )
                .padding(.bottom, 32)
            }
            .padding()
            .background(Color(.white))
            .clipShape(RoundedCorner(radius: 24, corners: [.topLeft, .topRight]))
        }
        .background(Color("BlackColor"))
        .edgesIgnoringSafeArea(.bottom)
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                HStack(alignment: .center, spacing: 16) {
                    Button(action: {
                        component.onEvent(event: CartViewEventOnBackClick())
                    }) {
                        Image(systemName: "chevron.backward")
                            .foregroundColor(.white) // цвет иконки
                            .frame(width: 36, height: 36)
                            .background(Circle().fill(Color("DarkGrayColor"))) // чёрный круглый фон
                    }
                    .buttonStyle(PlainButtonStyle())
                    Text("Корзина")
                        .font(.system(size: 17, weight: .regular, design: .rounded))
                        .foregroundColor(.white)
                }
            }
        }
    }
}

struct CartItemView: View {
    let item: CartItemModel
    let onAdd: (CartItemModel) -> Void
    let onRemove: (CartItemModel) -> Void

    var body: some View {
        HStack(spacing: 20) {
            VStack(alignment: .leading) {
                Text(item.title)
                    .font(.system(size: 18, weight: .regular, design: .rounded))
                    .foregroundColor(.white)
                Text("\(Int(item.price)) ₽")
                    .font(.system(size: 20, weight: .bold, design: .rounded))
                    .foregroundColor(.white)
            }
            Spacer()
            CartButton(
                quantity: Binding(
                    get: { Int(item.quantity) },
                    set: { _ in }
                ),
                onAdd: {
                    onAdd(item)
                },
                onIncrease: {
                    print("on add clicked")
                    onAdd(item)
                },
                onDecrease: {
                    onRemove(item)
                },
                foregroundColor: Color(.white)
            )
        }
    }
}
