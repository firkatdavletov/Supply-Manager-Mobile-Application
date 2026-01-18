//
//  PrimaryBUtton.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 27/10/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct SubtitleButton: View {
    let title: String
    let subtitle: String
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
                VStack {
                    Text(title)
                        .font(AppTypography.titleMedium)
                        .foregroundColor(Color.onPrimaryContainer)
                    Text(subtitle)
                        .font(AppTypography.bodySmall)
                        .foregroundColor(Color.onPrimaryContainer)
                }
                .frame(maxWidth: .infinity)
                .padding(12)
                .background(enabled ? Color.primaryContainer : Color.primaryContainer.opacity(0.5))
                .cornerRadius(25)
            }
        )
        .disabled(!enabled)
        .onAppear {
            
        }
    }
}

#Preview {
    SubtitleButton(
        title: "Кнопка",
        subtitle: "Test",
        onClick: {
            
        },
        enabled: false
    )
    .padding(.horizontal)
}
