//
//  VerificationContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 10/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import SwiftUI

struct VerifyBySmsContent: View {
    let phoneNumber: String
    let isLoading: Bool
    let onCodeChanged: (String) -> Void
    let onBack: () -> Void
    
    @State private var code: String = ""
    @FocusState private var isTextFieldFocused: Bool
    
    var body: some View {
        VStack(spacing: 0) {
            Text("Подтверждение")
                .foregroundColor(.white)
                .font(.system(size: 30, weight: .bold, design: .rounded))
                .bold()
                .padding(.top, 132)
            
            Text("Код отправлен на номер +\(phoneNumber)")
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
                                onCodeChanged(value)
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
                
                Button(action: onBack) {
                    Text("ВЕРНУТЬСЯ")
                        .foregroundColor(isLoading ? Color.gray : Color("PrimaryColor"))
                        .font(.system(size: 14, weight: .bold, design: .rounded))
                        .padding(.bottom, 8)
                }
                .padding(.vertical, 24)
                .disabled(isLoading)
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
