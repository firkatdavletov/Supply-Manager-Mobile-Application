//
//  PaymentContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 12/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//


import SwiftUI
import Shared

struct PaymentContent: View {
    let totalAmount: Int64
    let onBackButtonClicked: () -> Void
    let onConfirmClicked: () -> Void

    @State private var companyName: String = ""
    @State private var contactName: String = ""
    @State private var email: String = ""
    @State private var contactPhone: String = ""

    var body: some View {
        VStack(spacing: 0) {
            header
            ScrollView {
                VStack(spacing: 24) {
                    contactForm
                    bottomSummary
                }
                .padding(.vertical, 16)
            }
            PrimaryButton(
                title: "Заказать",
                onClick: onConfirmClicked,
                enabled: isFormValid
            )
            .padding()
        }
    }
}

extension PaymentContent {
    private var header: some View {
        HStack {
            Button(action: onBackButtonClicked) {
                Image(systemName: "chevron.left")
                    .foregroundColor(Color.onPrimaryContainer)
            }
            Spacer()
            Text("Оформление заказа")
                .font(AppTypography.titleLarge)
                .bold()
                .foregroundColor(Color.onPrimaryContainer)
            Spacer()
        }
        .padding()
        .background(Color.blue)
    }
}

extension PaymentContent {
    private var contactForm: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Контактные данные")
                .font(AppTypography.titleMedium)
                .foregroundStyle(Color.onBackground)
                .padding(.horizontal, 16)

            StyledTextField(
                value: companyName,
                placeholder: "Компания",
                isError: companyName.isEmpty == false && trimmedCompanyName.isEmpty,
                onChange: { companyName = String($0.prefix(100)) }
            )
            .padding(.horizontal, 16)

            StyledTextField(
                value: contactName,
                placeholder: "Контактное лицо",
                isError: contactName.isEmpty == false && trimmedContactName.isEmpty,
                onChange: { contactName = String($0.prefix(100)) }
            )
            .padding(.horizontal, 16)

            StyledTextField(
                value: email,
                placeholder: "Электронная почта",
                isError: email.isEmpty == false && isEmailValid == false,
                onChange: { email = String($0.prefix(100)) }
            )
            .padding(.horizontal, 16)

            StyledTextField(
                value: contactPhone,
                placeholder: "Номер телефона",
                isError: contactPhone.isEmpty == false && isPhoneValid == false,
                onChange: { newValue in
                    contactPhone = sanitizePhone(newValue)
                }
            )
            .padding(.horizontal, 16)
        }
    }
}

extension PaymentContent {
    private var bottomSummary: some View {
        VStack(spacing: 12) {
            HStack {
                Text("Итого:")
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(Color.onBackground)
                Spacer()
                Text("\(totalAmount.asCurrency())")
                    .font(AppTypography.titleLarge)
                    .foregroundStyle(Color.onBackground)
            }
        }
        .padding(.horizontal, 16)
    }
}

extension PaymentContent {
    private var trimmedCompanyName: String {
        companyName.trimmingCharacters(in: .whitespacesAndNewlines)
    }

    private var trimmedContactName: String {
        contactName.trimmingCharacters(in: .whitespacesAndNewlines)
    }

    private var trimmedEmail: String {
        email.trimmingCharacters(in: .whitespacesAndNewlines)
    }

    private var isEmailValid: Bool {
        trimmedEmail.contains("@") && trimmedEmail.contains(".")
    }

    private var isPhoneValid: Bool {
        contactPhone.filter(\.isNumber).count >= 10
    }

    private var isFormValid: Bool {
        trimmedCompanyName.isEmpty == false &&
        trimmedContactName.isEmpty == false &&
        isEmailValid &&
        isPhoneValid
    }

    private func sanitizePhone(_ value: String) -> String {
        let filtered = value.filter {
            $0.isNumber || $0 == "+" || $0 == " " || $0 == "(" || $0 == ")" || $0 == "-"
        }
        return String(filtered.prefix(20))
    }
}
