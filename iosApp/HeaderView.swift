//
//  HeaderView.swift
//  
//
//  Created by Фиркат Давлетов on 16/12/2025.
//
import SwiftUI

struct HeaderView: View {
    let onBack: () -> Void
    
    var body: some View {
        HStack {
            Button(action: onBack) {
                Image(systemName: "chevron.left")
                .foregroundColor(Color.onPrimaryContainer)
            }
            Spacer()
            Text("Корзина")
                .font(AppTypography.titleLarge)
                .bold()
                .foregroundColor(Color.onPrimaryContainer)
            Spacer()
        }
        .padding()
        .background(Color.primaryContainer)
    }
}
