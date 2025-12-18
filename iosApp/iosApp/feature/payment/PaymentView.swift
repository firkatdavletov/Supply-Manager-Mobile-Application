//
//  OrderView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 07/07/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct PaymentView: View {
    let component: PaymentComponent
    
    @StateValue private var state: PaymentViewState
    @StateValue private var dialogSlot: ChildSlot<AnyObject, DialogComponent>
   
    @State private var activeSheet: PaymentModalType?
    
    init(component: PaymentComponent) {
        self.component = component
        _state = StateValue(component.state)
        _dialogSlot = StateValue(component.dialogSlot)
    }
    
    var body: some View {
        PaymentContent(
            deliveryType: state.deliveryType,
            addressString: state.addressString,
            departmentName: state.departmentName,
            isPrivateHome: state.isPrivateHome,
            entrance: state.entrance,
            entranceInputError: state.entranceInputError,
            flat: state.flat,
            flatInputError: state.flatInputError,
            comment: state.comment,
            totalAmount: Int(state.totalAmount),
            deliveryPrice: Int(state.deliveryPrice),
            productPrice: Int(state.productPrice),
            paymentTypes: state.paymentTypes,
            onChangeDeliveryType: { deliveryType in
                component.onEvent(event: PaymentViewEventOnChangeDeliveryType(deliveryType: deliveryType))
            },
            onBackButtonClicked: {
                component.onEvent(event: PaymentViewEventOnBackButtonClicked())
            },
            onSelectAddress: {
                component.onEvent(event: PaymentViewEventOnChangeAddress())
            },
            onConfirmClicked: {
                component.onEvent(event: PaymentViewEventOnConfirmButtonClicked())
            },
            onIsPrivateHouseChanged: { value in
                component.onEvent(event: PaymentViewEventOnIsPrivateHouseChanged(isPrivateHouse: value))
            },
            onEntranceChanged: { entrance in
                component.onEvent(event: PaymentViewEventOnEntranceChanged(value: entrance))
            },
            onFlatChanged: { flat in
                component.onEvent(event: PaymentViewEventOnFlatChanged(value: flat))
            },
            onCommentChanged: { comment in
                component.onEvent(event: PaymentViewEventOnCommentChanged(value: comment))
            }
        )
        .navigationBarBackButtonHidden(true)
    }
}

extension View {
    @ViewBuilder func customAlert<T, A>(
        item: T?,
        onDismiss: @escaping (T) -> Void,
        title: (T) -> Text,
        message: (T) -> Text,
        actions: (T) -> A
    ) -> some View where A : View {
        if let item = item {
            alert(
                title(item),
                isPresented: Binding(get: { true }, set: {_,_ in onDismiss(item) }),
                actions: { actions(item) },
                message: { message(item) }
            )
        } else {
            self
        }
    }
}

struct CardPaymentView: View {
    let paymentService: PaymentService
    var callback: (_ crypto: String) -> Void
    @Environment(\.dismiss) var dismiss
    
    @State private var cardNumber: String = ""
    @State private var expiryDate: String = ""
    @State private var cvc: String = ""
    @State private var cardholderName: String = ""
    @FocusState private var isTextFieldFocused: Bool
    
    var body: some View {
        VStack(spacing: 16) {
            Capsule()
                .fill(Color.gray.opacity(0.4))
                .frame(width: 40, height: 5)
                .padding(.top, 8)
            
            Text("ОПЛАТА КАРТОЙ")
                .font(.system(size: 16, weight: .regular, design: .rounded))
                .foregroundColor(Color("DarkGrayColor"))
            Spacer()
            Text("НОМЕР КАРТЫ")
                .font(.system(size: 14, weight: .regular, design: .rounded))
                .foregroundColor(Color("DarkGrayColor"))
            TextField("0000 0000 0000 0000", text: $cardNumber)
                .cardTextFieldStyle()
                .focused($isTextFieldFocused)
                .onChange(of: cardNumber) { newValue in
                    // Оставляем только цифры
                        let digits = newValue.filter { $0.isNumber }
                        
                        // Ограничиваем до 16 цифр
                        let limited = String(digits.prefix(16))
                        
                        // Форматируем в группы по 4 цифры
                        var result = ""
                        for (index, char) in limited.enumerated() {
                            if index != 0 && index % 4 == 0 {
                                result.append(" ")
                            }
                            result.append(char)
                        }
                        
                        // Обновляем текст
                        cardNumber = result
                }
            
            HStack {
                VStack {
                    Text("ММ/ГГ")
                        .font(.system(size: 14, weight: .regular, design: .rounded))
                        .foregroundColor(Color("DarkGrayColor"))
                    TextField("00/00", text: $expiryDate)
                        .cardTextFieldStyle()
                        .onChange(of: expiryDate) { newValue in
                            // Удаляем все, кроме цифр
                            let digits = newValue.filter { $0.isNumber }

                            // Ограничиваем до 4 цифр
                            let limited = String(digits.prefix(4))

                            // Форматируем как MM/YY
                            var result = ""
                            for (index, char) in limited.enumerated() {
                                if index == 2 {
                                    result.append("/")
                                }
                                result.append(char)
                            }

                            // Обновляем поле
                            expiryDate = result
                        }
                }
                VStack {
                    Text("CVC")
                        .font(.system(size: 14, weight: .regular, design: .rounded))
                        .foregroundColor(Color("DarkGrayColor"))
                    TextField("****", text: $cvc)
                        .cardTextFieldStyle()
                        .onChange(of: cvc) { newValue in
                            // Удаляем все, кроме цифр
                            let digits = newValue.filter { $0.isNumber }

                            // Ограничиваем до 4 цифр
                            let limited = String(digits.prefix(4))

                            // Обновляем поле
                            cvc = limited
                        }
                }
            }
            Text("ИМЯ ВЛАДЕЛЬЦА")
                .font(.system(size: 14, weight: .regular, design: .rounded))
                .foregroundColor(Color("DarkGrayColor"))
            TextField("IVAN IVANOV", text: $cardholderName)
                .cardTextFieldStyle()
                .autocapitalization(.words)
                .onChange(of: cardholderName) { newValue in
                    let limited = String(newValue.prefix(40))
                    cardholderName = limited.uppercased()
                }
            
            Spacer()
            
            ConfirmButton(
                title: "ОПЛАТИТЬ",
                onConfirm: {
                    let crypto = paymentService.createCryptogram(
                        cardNumber: cardNumber,
                        expDate: expiryDate,
                        cvv: cvc,
                        merchantPublicID: "",
                        publicKey: "",
                        keyVersion: 1
                    )
                    if (crypto != nil) {
                        callback(crypto!)
                        dismiss()
                    }
                },
                isLoading: false,
                isDisabled: false
            )
            
            Button(action: {
                dismiss()
            }) {
                Text("ВЕРНУТЬСЯ")
                    .foregroundColor(Color("PrimaryColor"))
                    .font(.system(size: 14, weight: .bold, design: .rounded))
                    .padding(.bottom, 8)
            }
            .padding(.vertical, 24)
        }
        .padding()
        .background(Color(UIColor.systemBackground))
        .cornerRadius(20)
    }
}

struct PaymentTypeView : View {
    let selected: Bool
    let title: String
    let key: String
    let onSelect: (String) -> Void
    
    var body: some View {
        VStack {
            Image(systemName: "banknote")
                .foregroundColor(Color("PrimaryColor"))
                .frame(width: 85, height: 72)
                .background(Color("IceBlue"))
                .cornerRadius(12)
                .overlay(
                    RoundedRectangle(cornerRadius: 12)
                        .stroke(selected ? Color("PrimaryColor") : Color.clear, lineWidth: 2)
                )
            Text(title)
                .multilineTextAlignment(.center)
                .font(.system(size: 14, weight: .regular, design: .rounded))
                .foregroundColor(Color("DarkGrayColor"))
                .frame(width: 85)
        }
        .onTapGesture {
            onSelect(key)
        }
    }
}
