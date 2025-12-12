//
//  SwiftUIView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 24/07/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct CurrentOrderView: View {
    let component: CurrentOrderComponent
    @StateValue private var state: CurrentOrderViewState
    
    init(component: CurrentOrderComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    var body: some View {
        VStack {
            LazyVStack {
                ForEach(state.items, id: \.name) { item in
                    OrderItemView(title: item.name, count: Int(item.quantity))
                }
            }
        }
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                HStack(alignment: .center, spacing: 16) {
                    Button(action: {
                        component.onEvent(event: CurrentOrderViewEventOnBackClicked())
                    }) {
                        Image(systemName: "chevron.backward")
                            .foregroundColor(.white) // цвет иконки
                            .frame(width: 36, height: 36)
                            .background(Circle().fill(Color("DarkGrayColor"))) // чёрный круглый фон
                    }
                    .buttonStyle(PlainButtonStyle())
                    Text("Заказ №\(state.number)")
                        .font(.system(size: 17, weight: .regular, design: .rounded))
                        .foregroundColor(Color("DarkGrayColor"))
                }
            }
        }
        
    }
}

struct OrderItemView: View {
    let title: String
    let count: Int
    
    var body: some View {
        HStack {
            Text(title)
            Text("x")
            Text("\(count)")
        }
    }
}
