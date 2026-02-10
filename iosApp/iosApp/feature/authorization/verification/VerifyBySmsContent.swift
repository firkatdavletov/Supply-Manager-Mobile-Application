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
        GeometryReader { geometry in
            VStack(spacing: 0) {
                contentView()
                    .padding(.horizontal, 16)
                    .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        }
        .onAppear {
            DispatchQueue.main.async {
                isTextFieldFocused = true
            }
        }
    }

    @ViewBuilder
    private func headerView() -> some View {
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
    }

    @ViewBuilder
    private func contentView() -> some View {
        VStack {
            codeInputSection()
            backButtonView()
        }
        .padding()
    }

    @ViewBuilder
    private func codeInputSection() -> some View {
        Text("Код подтверждения:")
            .font(.system(size: 13, weight: .regular, design: .rounded))
            .foregroundStyle(Color("DarkGrayColor"))
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(.top, 24)

        codeInputField()
    }

    @ViewBuilder
    private func codeInputField() -> some View {
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
            .frame(maxWidth: .infinity)
            .font(.system(size: 14, weight: .regular, design: .rounded))
            .foregroundColor(Color("DarkGrayColor"))
            .multilineTextAlignment(.center)
            .lineLimit(1)
            .focused($isTextFieldFocused)
            .onChange(of: code) { newValue in
                let limited = sanitizeCode(newValue)

                if limited.count == 4 {
                    dismissKeyboard()
                }

                code = limited
            }
        }
        .frame(height: 62)
        .frame(maxWidth: .infinity)
        .background(Color("IceBlue"))
        .cornerRadius(10)
    }

    @ViewBuilder
    private func backButtonView() -> some View {
        Button(action: onBack) {
            Text("ВЕРНУТЬСЯ")
                .foregroundColor(isLoading ? Color.gray : Color.blue)
                .font(.system(size: 14, weight: .bold, design: .rounded))
                .padding(.bottom, 8)
        }
        .padding(.vertical, 24)
        .disabled(isLoading)
    }

    private func sanitizeCode(_ value: String) -> String {
        let digits = value.filter { $0.isNumber }
        return String(digits.prefix(4))
    }

    private func dismissKeyboard() {
        UIApplication.shared.sendAction(
            #selector(UIResponder.resignFirstResponder),
            to: nil,
            from: nil,
            for: nil
        )
    }
}
