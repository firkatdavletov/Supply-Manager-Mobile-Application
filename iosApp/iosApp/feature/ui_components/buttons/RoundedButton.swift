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
                    .frame(height: 50)
                    .background(enabled ? Color.primaryContainer : Color.primaryContainer.opacity(0.2))
                    .foregroundColor(Color.onPrimaryContainer)
                    .cornerRadius(25)
            }
        )
        .disabled(!enabled)
    }
}

#Preview {
    RoundedButton(
        title: "Кнопка",
        onClick: {
            
        },
        enabled: false
    )
    .padding(.horizontal)
}
