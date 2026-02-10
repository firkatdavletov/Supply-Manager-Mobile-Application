//
//  PrimaryBUtton.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 27/10/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct PrimaryButton: View {
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
                    .background(enabled ? Color.blue : Color.blue.opacity(0.5))
                    .foregroundColor(Color.white)
                    .cornerRadius(25)
            }
        )
        .disabled(!enabled)
        .onAppear {
            
        }
    }
}

#Preview {
    PrimaryButton(
        title: "Кнопка",
        onClick: {
            
        },
        enabled: false
    )
    .padding(.horizontal)
}
