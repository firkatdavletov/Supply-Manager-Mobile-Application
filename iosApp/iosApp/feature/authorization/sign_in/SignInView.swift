//
//  SignInView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Combine
import Shared


struct SignInView: View {
    let component: SignInComponent
    
    @StateValue private var state: SignInViewState
    @State private var phoneNumber: String = ""
    @StateObject private var keyboard = KeyboardResponder()
    @FocusState private var isTextFieldFocused: Bool
    
    init(component: SignInComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    
    var body: some View {
        VStack(spacing: 0) {
            Text("Войти")
                .foregroundColor(.white)
                .font(.system(size: 30, weight: .bold, design: .rounded))
                .bold()
                .padding(.top, 132)
            
            Text("Пожалуйста, подтвердите номер телефона")
                .foregroundColor(.white)
                .font(.system(size: 16, weight: .regular, design: .rounded))
                .bold()
                .padding(.vertical, 16)
            
            VStack {
                Text("Номер телефона:")
                    .font(.system(size: 13, weight: .regular, design: .rounded))
                    .foregroundStyle(Color("DarkGrayColor"))
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.top, 24)
                HStack(spacing: 4) {
                    Text("+7")
                        .font(.system(size: 14, weight: .regular, design: .rounded))
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
                                component.onEvent(event: SignInViewEventOnPhoneNumberChanged(phone: value))
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
                
                if (state.isLoading) {
                    Spacer()
                        .frame(height: 46)
                } else {
                    HStack(spacing: 8) {
                        ForEach(state.authTypes, id: \.self) { type in
                            let title = switch (type) {
                                case "sms": "СМС"
                                case "call": "Телеграм"
                                default: ""
                            }
                            
                            let isSelected = state.selectedAuthType == type
                            let backgroundColor = isSelected ? Color("PrimaryColor") : Color.white
                            let borderColor = isSelected ? Color("PrimaryColor") : Color("DarkGrayColor")
                            let textColor = isSelected ? Color.white : Color("DarkGrayColor")

                            if !title.isEmpty {
                                Button(action: {
                                    UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
                                    component.onEvent(event: SignInViewEventAuthTypeClicked(authType: type))
                                }) {
                                    Text(title)
                                        .font(.system(size: 14, weight: .medium))
                                        .foregroundColor(textColor)
                                        .frame(maxWidth: .infinity, minHeight: 46)
                                        .background(backgroundColor)
                                        .overlay(
                                            RoundedRectangle(cornerRadius: 12)
                                                .stroke(borderColor, lineWidth: 1)
                                        )
                                        .cornerRadius(12)
                                }
                            }
                        }
                    }
                }
                
                Spacer()
                
                Text("Нажимая \"Подтвердить\", вы соглашаетесь с условиями использования и политикой конфиденциальности.")
                    .font(.footnote)
                    .foregroundColor(Color("DarkGrayColor"))
                    .multilineTextAlignment(.leading)
                    .frame(maxWidth: .infinity, alignment: .leading)

                ConfirmButton(
                    title: "ПОДТВЕРДИТЬ",
                    onConfirm: {
                        component.onEvent(event: SignInViewEventLoginClicked(phoneNumber: phoneNumber))
                    },
                    isLoading: state.isLoading,
                    isDisabled: !state.confirmEnabled
                )

                Button(action: {
                    print("Авторизуюсь позже")
                }) {
                    Text("ПРОПУСТИТЬ")
                        .foregroundColor(state.isLoading ? Color.gray : Color("PrimaryColor"))
                        .font(.system(size: 14, weight: .bold, design: .rounded))
                        .padding(.bottom, 8)
                }
                .padding(.vertical, 24)
                .disabled(state.isLoading)
            }
            .padding()
            .background(
                Color.white
                    .clipShape(RoundedCorner(radius: 20, corners: [.topLeft, .topRight]))
            )
        }
        .edgesIgnoringSafeArea([.bottom, .top])
        .frame(maxWidth: .infinity)
        .navigationBarBackButtonHidden(true)
        .background(Color("BlackColor"))
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                isTextFieldFocused = true
            }
        }
    }
}
