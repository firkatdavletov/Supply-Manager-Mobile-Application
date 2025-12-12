//
//  CatalogView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct CatalogView: View {
    let root: DefaultCatalogComponent
    
    @StateValue private var state: CatalogViewState
    
    init(root: CatalogComponent) {
        self.root = root as! DefaultCatalogComponent
        _state = StateValue(self.root.state)
    }
    
    let columns = [
            GridItem(.flexible(), spacing: 16),
            GridItem(.flexible(), spacing: 16)
        ]
    
    var body: some View {
        ZStack(alignment: .bottom) {
            ScrollView {
                LazyVGrid(columns: columns, spacing: 16) {
                    ForEach(state.products, id: \.id) { product in
                        ProductCardView(
                            product: product,
                            onAddToCart: { id in
                                root.onEvent(event: CatalogViewEventOnAddToCart(product: product))
                            },
                            onIncrease: { id in
                                root.onEvent(event: CatalogViewEventOnAddToCart(product: product))
                            },
                            onDecrease: { id in
                                root.onEvent(event: CatalogViewEventOnRemoveFromCart(product: product))
                            }
                        )
                    }
                }
                .padding()
            }
            if state.amount > 0 {
                HStack {
                    Text("Итого: \(String(format: "%.2f", state.amount)) ₽")
                        .font(.headline)
                        .foregroundColor(.white)
                    Spacer()
                    Button(action: {
                        root.onEvent(event: CatalogViewEventOnCartButtonClicked())
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
                .transition(.move(edge: .bottom).combined(with: .opacity))
                .animation(.easeInOut, value: state.amount)
            }
        }
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                HStack(alignment: .center, spacing: 16) {
                    Button(action: {
                        root.onEvent(event: CatalogViewEventOnBackClicked())
                    }) {
                        Image(systemName: "chevron.backward")
                            .foregroundColor(.white) // цвет иконки
                            .frame(width: 36, height: 36)
                            .background(Circle().fill(Color("DarkGrayColor"))) // чёрный круглый фон
                    }
                    .buttonStyle(PlainButtonStyle())
                    Text(state.title)
                        .font(.system(size: 17, weight: .regular, design: .rounded))
                        .foregroundColor(Color("DarkGrayColor"))
                }
            }
        }
    }
}
