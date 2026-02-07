//
//  SignInContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 16/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct SignInContent: View {
    let onPhoneNumberEntered: (String) -> Void
    let isLoading: Bool
    let authTypes: [String]
    let onAuthTypeClicked: (String) -> Void
    let onLoginButtonClicked: (String) -> Void
    let onBackClicked: () -> Void

    @State private var phoneNumber: String = ""
    @StateObject private var keyboard = KeyboardResponder()
    @FocusState private var isTextFieldFocused: Bool

    var body: some View {
        
        VStack {
            SignInTopBar()
            
            ScrollView {
                inputView()
                    .padding()
                
                VStack {
                    ForEach(authTypes, id: \.self) { type in
                        Text(type)
                    }
                }
            }
        }
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                isTextFieldFocused = true
            }
        }
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

#Preview {
    SignInContent(
        onPhoneNumberEntered: { String in

        },
        isLoading: false,
        authTypes: ["sms"]) { String in

    } onLoginButtonClicked: { String in

    } onBackClicked: {

    }
}
