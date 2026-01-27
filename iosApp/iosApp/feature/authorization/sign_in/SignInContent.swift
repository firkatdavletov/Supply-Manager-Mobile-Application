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
        ZStack {
            titleView
            
            VStack {
                Text("Номер телефона:")
                    .font(AppTypography.titleSmall)
                    .foregroundStyle(Color("DarkGrayColor"))
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.top, 24)
                
                phoneInputView
                
                Text("Мы используем номер только для входа и связи по заказу. Подтверждая номер телефона, вы соглашаетесь с условиями использования и политикой конфиденциальности.")
                    .font(.footnote)
                    .foregroundColor(Color("DarkGrayColor"))
                    .multilineTextAlignment(.leading)
                    .frame(maxWidth: .infinity, alignment: .leading)
                Spacer()
                
                if (isLoading) {
                    Spacer()
                        .frame(height: 46)
                } else {
                    ScrollView {
                        VStack(spacing: 8) {
                            ForEach(authTypes, id: \.self) { type in
                                SubtitleButton(
                                    title: type == "sms" ? "СМС" :
                                        type == "call" ? "По звонку" : "",
                                    subtitle: "Бесплатный звонок, подтверждение автоматически",
                                    onClick: {
                                        onAuthTypeClicked(type)
                                    },
                                    enabled: true
                                )
                            }
                        }
                    }
                }
            }
            .padding()
            .background(
                Color.white
                    .clipShape(RoundedCorner(radius: 20, corners: [.topLeft, .topRight]))
            )
            .padding(.top, 236)
        }
        .background(Color.darkCosmicBlue)
//        .edgesIgnoringSafeArea([.bottom, .top])
        .frame(maxWidth: .infinity)
        .navigationBarBackButtonHidden(true)
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                isTextFieldFocused = true
            }
        }
    }
}

extension SignInContent {
    private var titleView: some View {
        VStack(spacing: 0) {
            HStack {
                IconButton(
                    systemName: "arrow.backward",
                    tint: Color.white,
                    foreground: Color.darkCosmicBlue,
                    action: onBackClicked
                )
                .padding()
                Spacer()
            }
            Text("Подтверждение номера")
                .foregroundColor(.white)
                .font(AppTypography.headlineMedium)
                .bold()
            
            if (authTypes.count <= 1) {
                Text("Подтвердите номер, чтобы продолжить")
                    .foregroundColor(.white)
                    .font(AppTypography.titleMedium)
                    .bold()
                    .padding(.vertical, 16)
                    .multilineTextAlignment(.center)
            } else {
                Text("Выберите удобный способ подтверждения")
                    .foregroundColor(.white)
                    .font(AppTypography.titleMedium)
                    .bold()
                    .padding(.vertical, 16)
                    .multilineTextAlignment(.center)
            }
            
            Spacer()
        }
        .frame(maxWidth: .infinity)
    }
}

extension SignInContent {
    private var phoneInputView: some View {
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
