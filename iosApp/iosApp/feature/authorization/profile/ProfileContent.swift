//
//  ProfileContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 16/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct ProfileContent: View {
    let name: String
    let phone: String
    let isLoading: Bool
    let onNameChanged: (String) -> Void
    let onSave: () -> Void
    let onDelete: () -> Void
    let onLogout: () -> Void
    let onBack: () -> Void
    
    var body: some View {
        VStack {
            HStack {
                Button(action: onBack) {
                    Image(systemName: "chevron.left")
                        .foregroundColor(Color.onPrimaryContainer)
                }
                Spacer()
                Text("Профиль")
                    .font(AppTypography.titleLarge)
                    .bold()
                    .foregroundColor(Color.onPrimaryContainer)
                Spacer()
                Button(action: onLogout) {
                    Image(systemName: "figure.walk.departure")
                        .foregroundColor(Color.onPrimaryContainer)
                }
            }
            .padding()
            .background(Color.primaryContainer)
            
            StyledTextField(
                value: name,
                placeholder: "Имя",
                isError: false,
                onChange: onNameChanged
            )
            .padding(.horizontal)
            .padding(.top)
            
            StyledTextField(
                value: phone,
                placeholder: "Телефон",
                isError: false,
                onChange: { value in }
            )
            .disabled(true)
            .padding(.horizontal)
            .padding(.top, 4)
            
            Spacer()
            
            RoundedButton(
                title: "Сохранить",
                onClick: onSave,
                enabled: !isLoading
            )
            .padding(.horizontal)
            
            Button(
                action: onDelete,
                label: {
                    Text("ВУдалить")
                        .font(AppTypography.bodyMedium)
                        .foregroundStyle(Color.primaryContainer)
                }
            )
            .padding(.horizontal)
        }
    }
}

#Preview {
    ProfileContent(
        name: "Firkat",
        phone: "79061003700",
        isLoading: false,
        onNameChanged: { String in
            
        },
        onSave: {
            
        },
        onDelete: {
            
        },onLogout: {
            
        }, onBack: {
            
        }
    )
}
