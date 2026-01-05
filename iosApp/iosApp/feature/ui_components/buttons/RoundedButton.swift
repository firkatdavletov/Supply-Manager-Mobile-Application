//
//  PrimaryBUtton.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 27/10/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct RoundedButton: View {
    let title: String
    let onClick: () -> Void
    let background: Color
    let foreground: Color
    let enabled: Bool
    
    
    var body: some View {
        Button(
            action: {
                if (enabled) {
                    onClick()
                }
            },
            label: {
                Text(title)
                    .font(AppTypography.titleMedium.bold())
                    .frame(maxWidth: .infinity)
                    .padding(12)
                    .background(enabled ? background : Color.primaryContainer)
                    .foregroundColor(enabled ? foreground : Color.onPrimaryContainer)
                    .cornerRadius(25)
            }
        )
    }
}

#Preview {
    RoundedButton(
        title: "Кнопка",
        onClick: {
            
        },
        background: Color.primaryContainer,
        foreground: Color.onPrimaryContainer,
        enabled: false
    )
    .padding(.horizontal)
}
