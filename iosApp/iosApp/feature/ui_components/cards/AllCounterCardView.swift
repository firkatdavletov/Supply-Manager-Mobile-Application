//
//  CounterCardView 2.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 28/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//
import SwiftUI

struct AllCounterCardView: View {
    
    let count: Int

    var body: some View {
        VStack(spacing: 8) {
            Text("\(count)")
                .font(.system(size: 32, weight: .bold))
                .foregroundColor(.black)
            
            Text("Всего заявок")
                .font(.system(size: 14, weight: .medium))
                .foregroundColor(.gray)
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 20)
        .background(.white)
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(Color.gray.opacity(0.4), lineWidth: 1)
        )
        .cornerRadius(16)
        .shadow(color: .black.opacity(0.05), radius: 4, x: 0, y: 2)
    }
}
