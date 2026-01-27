//
//  ConfirmButton.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 20/06/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct SecondaryButton: View {
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
                    .font(AppTypography.titleMedium)
                    .frame(maxWidth: .infinity)
                    .padding(12)
                    .background(Color.background.opacity(0.2))
                    .foregroundColor(enabled ? Color.primaryContainer : Color.primaryContainer.opacity(0.5))
                    .overlay(
                        RoundedRectangle(cornerRadius: 25)
                            .stroke(enabled ? Color.primaryContainer : Color.primaryContainer.opacity(0.5), lineWidth: 2)
                    )
                    .cornerRadius(25)
            }
        )
        .disabled(!enabled)
        .onAppear {
            
        }
    }
}

#Preview {
    SecondaryButton(title: "Test", onClick: {}, enabled: true)
}
