//
//  SignInContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 16/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct SignInContent: View {
    let login: String
    let password: String
    let onLoginChanged: (String) -> Void
    let onPasswordChanged: (String) -> Void
    let onLoginClicked: () -> Void
    let isLoading: Bool
    let isLoginEnabled: Bool
    let showBackButton: Bool
    let onBackClicked: () -> Void

    var body: some View {
        VStack(spacing: 12) {
            Text("Войти")
                .font(AppTypography.titleMedium)
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.bottom, 8)

            TextField(
                "Логин",
                text: Binding(
                    get: { login },
                    set: onLoginChanged
                )
            )
            .textInputAutocapitalization(.never)
            .autocorrectionDisabled()
            .padding(14)
            .background(Color("IceBlue"))
            .cornerRadius(10)

            SecureField(
                "Пароль",
                text: Binding(
                    get: { password },
                    set: onPasswordChanged
                )
            )
            .padding(14)
            .background(Color("IceBlue"))
            .cornerRadius(10)

            Spacer()

            Button(action: onLoginClicked) {
                Text("Войти")
                    .font(AppTypography.titleMedium)
                    .frame(maxWidth: .infinity)
                    .padding(12)
                    .background((isLoginEnabled && !isLoading) ? Color.blue : Color.gray.opacity(0.5))
                    .foregroundColor(Color.white)
                    .cornerRadius(25)
            }
            .disabled(!isLoginEnabled || isLoading)
            .padding(.bottom, 16)
        }
        .padding(.horizontal, 16)
        .padding(.top, 16)
        .overlay {
            if isLoading {
                ProgressView()
                    .scaleEffect(1.2)
            }
        }
    }
}
