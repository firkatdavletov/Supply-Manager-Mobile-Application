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
    let onPhoneNumberEntered: (String) -> Void
    let isLoading: Bool
    let authTypes: [AuthTypeModel]
    let onAuthTypeClicked: (String) -> Void
    let showBackButton: Bool
    let onBackClicked: () -> Void

    @State private var phoneNumber: String = ""
    @StateObject private var keyboard = KeyboardResponder()
    @FocusState private var isTextFieldFocused: Bool

    var body: some View {
        GeometryReader { geometry in
            VStack(spacing: 0) {
                inputView()
                    .padding(.horizontal, 16)
                    .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
            }
            .safeAreaInset(edge: .bottom, spacing: 0) {
                if !authTypes.isEmpty {
                    authTypeButtonsView()
                        .padding(.horizontal, 16)
                        .padding(.top, 8)
                        .padding(.bottom, authButtonsBottomPadding(bottomSafeAreaInset: geometry.safeAreaInsets.bottom))
                        .animation(.easeOut(duration: 0.2), value: keyboard.currentHeight)
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        }
        .ignoresSafeArea(.keyboard, edges: .bottom)
        .onAppear {
            DispatchQueue.main.async {
                isTextFieldFocused = true
            }
        }
    }

    @ViewBuilder
    private func authTypeButtonsView() -> some View {
        VStack(spacing: 8) {
            ForEach(authTypes, id: \.key) { authType in
                Button(action: {
                    onAuthTypeClicked(authType.key)
                }) {
                    Text(authType.title)
                        .font(AppTypography.titleMedium)
                        .frame(maxWidth: .infinity)
                        .padding(12)
                        .background(Color.blue)
                        .foregroundColor(Color.white)
                        .cornerRadius(25)
                }
                .disabled(isLoading)
            }
        }
    }

    private func authButtonsBottomPadding(bottomSafeAreaInset: CGFloat) -> CGFloat {
        max(keyboard.currentHeight - bottomSafeAreaInset, 0) + 16
    }
    
    @ViewBuilder
    private func inputView() -> some View {
        HStack(spacing: 4) {
            Text("+7")
                .font(AppTypography.titleSmall)
                .foregroundColor(Color("DarkGrayColor"))
                .frame(alignment: .trailing)
                .padding(.leading, 16)
            TextField(
                "(999)9999999",
                text: Binding(
                    get: {
                        phoneNumber
                    },
                    set: { value in
                        phoneNumber = value
                        if (value.count < 14) {
                            onPhoneNumberEntered(value)
                        }
                    }
                )
            )
            .keyboardType(.phonePad)
            .frame(height: 62)
            .font(.system(size: 14, weight: .regular, design: .rounded))
            .foregroundColor(Color("DarkGrayColor"))
            .lineLimit(1)
            .focused($isTextFieldFocused)
            .onChange(of: phoneNumber) { newValue in
                // Оставляем только цифры
                let digits = newValue.filter { $0.isNumber }

                // Ограничиваем до 10 цифр
                let limited = String(digits.prefix(10))

                // Форматируем: добавим ( и )
                var result = ""
                if !limited.isEmpty {
                    result += "("
                }
                if limited.count >= 1 {
                    result += String(limited.prefix(3))
                } else {
                    result += limited
                }
                if limited.count >= 4 {
                    result += ") " + limited.dropFirst(3)
                }

                phoneNumber = result
            }
        }
        .background(Color("IceBlue"))
        .cornerRadius(10)
    }
}
