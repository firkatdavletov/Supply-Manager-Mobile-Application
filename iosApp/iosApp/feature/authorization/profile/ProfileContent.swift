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
            }
            .padding()
            .background(Color.blue)
            ScrollView {
                VStack {
                    StyledTextField(
                        value: name,
                        placeholder: "Имя",
                        isError: false,
                        onChange: { value in }
                    )
                        .disabled(true)
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
                }
            }

            Spacer()

            PrimaryButton(
                title: "Выйти",
                onClick: onLogout,
                enabled: !isLoading
            )
            .padding(.horizontal)
        }
    }
}