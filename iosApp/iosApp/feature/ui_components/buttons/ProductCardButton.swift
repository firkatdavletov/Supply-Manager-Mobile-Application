//
//  ProductCardButton.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 05/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import SwiftUI

struct ProductCardButton: View {
    let quantity: Int
    let onAdd: () -> Void
    let onRemove: () -> Void
    
    var body: some View {
        HStack {
            Button(action: onRemove) {
                Image(systemName: "minus")
                    .foregroundColor(.onPrimaryContainer)
                    .frame(width: 50, height: 40)
            }

            Spacer()

            Text("В корзине \(quantity) шт.")
                .font(.system(size: 16, weight: .medium))
                .frame(minWidth: 20)
                .foregroundColor(.onPrimaryContainer)

            Spacer()

            Button(action: onAdd) {
                Image(systemName: "plus")
                    .foregroundColor(.onPrimaryContainer)
                    .frame(width: 50, height: 50)
            }
        }
        .frame(maxWidth: .infinity)
        .background(Color.primaryContainer)
        .clipShape(RoundedCorner(radius: 25))
    }
}
