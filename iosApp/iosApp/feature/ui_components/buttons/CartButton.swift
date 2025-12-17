//
//  CartButton.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 16/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct CartButton: View {
    let quantity: Int
    let onAdd: () -> Void
    let onRemove: () -> Void
    let foregroundColor: Color
    
    var body: some View {
        HStack() {
            if quantity == 0 {
                Spacer()
                Button(action: onAdd) {
                    Image(systemName: "plus")
                        .foregroundColor(.onPrimaryContainer)
                        .frame(width: 30, height: 30)
                }
                .background(Color.primaryContainer)
                .clipShape(RoundedCorner(radius: 15))
            } else {
                HStack(spacing: 8) {
                    Button(action: onRemove) {
                        Image(systemName: "minus")
                            .foregroundColor(.onPrimaryContainer)
                            .frame(width: 30, height: 30)
                    }
                    
                    Spacer()

                    Text("\(quantity)")
                        .font(.system(size: 16, weight: .medium))
                        .frame(minWidth: 20)
                        .foregroundColor(.onPrimaryContainer)
                    
                    Spacer()

                    Button(action: onAdd) {
                        Image(systemName: "plus")
                            .foregroundColor(.onPrimaryContainer)
                            .frame(width: 30, height: 30)
                    }
                }
                .frame(maxWidth: .infinity)
                .background(Color.primaryContainer)
                .clipShape(RoundedCorner(radius: 15))
            }
        }
    }
}

#Preview {
    CartButton(
        quantity: 5,
        onAdd: {
            
        },
        onRemove: {
            
        },
        foregroundColor: Color.primaryContainer
    )
}
