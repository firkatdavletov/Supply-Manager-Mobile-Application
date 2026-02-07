//
//  CategoryCardView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 09.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct CategoryCardView: View {
    let title: String
    let imageUrl: String?

    var body: some View {
        VStack(spacing: 8) {
            
            Text(title)
                .font(.system(size: 14, weight: .medium))
                .foregroundColor(Color.onBackground)
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
