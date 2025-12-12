//
//  VerificationView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct VerificationView: View {
    let component: VerificationComponent
    
    @StateValue private var state: VerifyViewState
    @State private var showSheet = false
    @FocusState private var isTextFieldFocused: Bool
    
    @State private var code: String = ""
    
    init(component: VerificationComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    
    var body: some View {
        VStack(spacing: 0) {
            Text("Подтверждение")
                .foregroundColor(.white)
                .font(.system(size: 30, weight: .bold, design: .rounded))
                .bold()
                .padding(.top, 132)
            Text("Код отправлен на номер +\(state.phoneNumber)")
                .foregroundColor(.white)
                .font(.system(size: 16, weight: .regular, design: .rounded))
                .bold()
                .padding(.vertical, 16)
            
            VStack {
                Text("Код подтверждения:")
                    .font(.system(size: 13, weight: .regular, design: .rounded))
                    .foregroundStyle(Color("DarkGrayColor"))
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.top, 24)
                
                HStack(spacing: 0) {
                    TextField(
                        "****",
                        text: Binding(
                            get: { code },
                            set: { value in
                                code = value
                                component.onEvent(event: VerifyViewEventOnCodeChanged(newValue: value))
                            }
                        )
                    )
                    .keyboardType(.phonePad)
                    .frame(height: 62)
                    .frame(maxWidth: .infinity) // расширяет TextField
                    .font(.system(size: 14, weight: .regular, design: .rounded))
                    .foregroundColor(Color("DarkGrayColor"))
                    .multilineTextAlignment(.center) // горизонтальное выравнивание текста
                    .lineLimit(1)
                    .focused($isTextFieldFocused)
                    .onChange(of: code) { newValue in
                        let digits = newValue.filter { $0.isNumber }
                        let limited = String(digits.prefix(4))

                        if limited.count == 4 {
                            UIApplication.shared.sendAction(
                                #selector(UIResponder.resignFirstResponder),
                                to: nil, from: nil, for: nil
                            )
                        }

                        code = limited
                    }
                }
                .frame(height: 62)
                .frame(maxWidth: .infinity) // растягивает весь HStack
                .background(Color("IceBlue"))
                .cornerRadius(10)
                
                Spacer()

                ConfirmButton(
                    title: "ПОДТВЕРДИТЬ",
                    onConfirm: {
                        
                    },
                    isLoading: state.isLoading,
                    isDisabled: !state.isLoading                )

                Button(action: {
                    component.onEvent(event: VerifyViewEventOnBackClicked())
                }) {
                    Text("ВЕРНУТЬСЯ")
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

struct VerificationBottomSheet: View {
    @State var isLoading: Bool
    let showTitle: Bool
    let onBackClick: () -> Void
    @Binding var code: String
    
    var body: some View {
        VStack {
            Capsule()
                .frame(width: 40, height: 5)
                .foregroundColor(Color("PrimaryColor"))
                .padding(.top, 8)
            if (showTitle) {
                VStack {
                    Text("Подтверждение")
                        .foregroundColor(Color("PrimaryColor"))
                        .font(.system(size: 30, weight: .bold, design: .rounded))
                        .bold()
                    
                    Text("Ведите код подтверждения")
                        .foregroundColor(Color("PrimaryColor"))
                        .font(.system(size: 16, weight: .regular, design: .rounded))
                        .bold()
                }
                .padding(.top, 24)
            }
            Spacer()
            Text("Код:")
                .font(.system(size: 13, weight: .regular, design: .rounded))
                .foregroundStyle(Color("DarkGrayColor"))
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.top, 24)
            HStack(spacing: 4) {
                TextField("****", text: $code)
                    .keyboardType(.phonePad)
                    .frame(height: 62)
                    .font(.system(size: 14, weight: .regular, design: .rounded))
                    .foregroundColor(Color("DarkGrayColor"))
                    .lineLimit(1)
                    .disabled(isLoading)
            }
                .padding(.horizontal)
                .background(Color("IceBlue"))
                .cornerRadius(10)
            
            Spacer()
            
            Button(action: {
                onBackClick()
            }) {
                Text("ВЕРНУТЬСЯ")
                    .foregroundColor(Color("PrimaryColor"))
                    .font(.system(size: 14, weight: .bold, design: .rounded))
                    .padding(.bottom, 8)
            }
                .padding(.top, 24)
                .disabled(isLoading)
        }
            .padding(.horizontal)
    }
}

