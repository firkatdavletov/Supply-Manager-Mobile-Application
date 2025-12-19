//
//  OrderItemView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 19/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct OrderItemView: View {
    let orderItem: OrderItemModel
    
    var body: some View {
        HStack {
            Text(orderItem.name)
            Spacer()
            Text("x")
            Text("\(orderItem.quantity)")
        }
    }
}
