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

    let deliveryType: DeliveryType
    let addressString: String?
    let departmentName: String?
    let isPrivateHome: Bool
    let entrance: String
    let entranceInputError: String?
    let flat: String
    let flatInputError: String?
    let comment: String
    let totalAmount: Int
    let deliveryPrice: Int
    let productPrice: Int
    let paymentTypes: [PaymentTypeModel]

    let onChangeDeliveryType: (DeliveryType) -> Void
    let onBackButtonClicked: () -> Void
    let onSelectAddress: () -> Void
    let onConfirmClicked: () -> Void
    let onIsPrivateHouseChanged: (Bool) -> Void
    let onEntranceChanged: (String) -> Void
    let onFlatChanged: (String) -> Void
    let onCommentChanged: (String) -> Void

    var body: some View {
        ZStack(alignment: .bottom) {
            ScrollView {
                VStack(alignment: .leading, spacing: 0) {

                    header()

                    deliveryTypeSelector()

                    if deliveryType == .delivery {
                        deliverySection()
                    } else {
                        pickupSection()
                    }

                    paymentSection()
                }
                .padding(.bottom, 160) // пространство под нижнюю панель
            }

            bottomSummary()
        }
        .edgesIgnoringSafeArea(.top)
    }
}

extension PaymentContent {
    private func header() -> some View {
        ZStack {
            HStack {
                Button(action: onBackButtonClicked) {
                    Image("ic_arrow_back_16")
                        .renderingMode(.template)
                        .foregroundColor(.white)
                }
                Spacer()
            }
            Text("Оформление заказа")
                .font(.title2)
                .foregroundColor(.white)
        }
        .padding(.horizontal, 8)
        .padding(.top, UIApplication.shared.safeTop)
        .frame(maxWidth: .infinity)
        .background(Color.primaryContainer)
    }
}

extension PaymentContent {
    private func deliveryTypeSelector() -> some View {
        HStack(spacing: 16) {
            SelectedButton(
                title: "Самовывоз",
                selected: deliveryType == .pickup,
                action: {
                    if deliveryType == .delivery { onChangeDeliveryType(.pickup) }
                }
            )
            SelectedButton(
                title: "Доставка",
                selected: deliveryType == .delivery,
                action: {
                    if deliveryType == .pickup { onChangeDeliveryType(.delivery) }
                }
            )
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 8)
    }
}

extension PaymentContent {

    private func deliverySection() -> some View {
        VStack(alignment: .leading, spacing: 16) {

            Text("Куда")
                .font(.title2)
                .padding(.horizontal, 16)

            HStack {
                if let addr = addressString {
                    Text(addr)
                        .font(.title3)
                        .foregroundColor(.primary)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }

                SelectedButton(
                    title: addressString == nil ? "Выбрать адрес" : "Изменить адрес",
                    selected: false,
                    action: onSelectAddress
                )
            }
            .padding(.horizontal, 16)

            if addressString != nil {
                HStack(spacing: 8) {
                    SelectedButton(
                        title: "Частный дом",
                        selected: isPrivateHome,
                        action: {
                            if !isPrivateHome { onIsPrivateHouseChanged(true) }
                        }
                    )
                    SelectedButton(
                        title: "Квартира/офис",
                        selected: !isPrivateHome,
                        action: {
                            if isPrivateHome { onIsPrivateHouseChanged(false) }
                        }
                    )
                }
                .padding(.horizontal, 16)

                if !isPrivateHome {
                    HStack(spacing: 8) {
                        StyledTextField(
                            value: entrance,
                            placeholder: "Подъезд",
                            isError: entranceInputError != nil,
                            onChange: onEntranceChanged
                        )

                        StyledTextField(
                            value: flat,
                            placeholder: "Квартира/офис",
                            isError: flatInputError != nil,
                            onChange: onFlatChanged
                        )
                    }
                    .padding(.horizontal, 16)
                }

                StyledTextField(
                    value: comment,
                    placeholder: "Комментарий",
                    isError: false,
                    onChange: onCommentChanged
                )
                .padding(.horizontal, 16)
            }
        }
    }
}

extension PaymentContent {
    private func pickupSection() -> some View {
        VStack(alignment: .leading, spacing: 8) {

            Text("Откуда")
                .font(.title2)

            if let department = departmentName {
                Text(department)
                    .font(.title3)
            }

            StyledTextField(
                value: comment,
                placeholder: "Комментарий",
                isError: false,
                onChange: onCommentChanged
            )
            .padding(.vertical, 8)

        }
        .padding(.horizontal, 16)
    }
}

extension PaymentContent {
    private func paymentSection() -> some View {
        VStack(alignment: .leading) {

            Text("Оплата")
                .font(.title2)
                .padding(.bottom, 16)

            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 8) {
                    ForEach(paymentTypes, id: \.id) { type in
                        SelectedButton(
                            title: type.title,
                            selected: type.selected,
                            action: {}
                        )
                    }
                }
            }
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 16)
    }
}

extension PaymentContent {
    private func bottomSummary() -> some View {
        VStack(spacing: 12) {

            HStack {
                Text("Стоимость продуктов:")
                Spacer()
                Text("\(productPrice) ₽")
            }
            .font(.title3)

            if deliveryType == .delivery {
                HStack {
                    Text("Стоимость доставки:")
                    Spacer()
                    Text(deliveryPrice == 0 ? "бесплатно" : "\(deliveryPrice) ₽")
                }
                .font(.title3)
            }

            HStack {
                Text("Итого:")
                Spacer()
                Text("\(totalAmount) ₽")
                    .font(.title3)
            }

            Button(action: onConfirmClicked) {
                Text("Заказать")
                    .frame(maxWidth: .infinity)
                    .padding()
            }
            .buttonStyle(.borderedProminent)
            .disabled(deliveryType == .delivery && addressString == nil)
        }
        .padding(.horizontal, 16)
        .padding(.bottom, UIApplication.shared.safeBottom)
        .background(Color(.systemBackground))
    }
}

extension UIApplication {
    var safeTop: CGFloat {
        let window = UIApplication.shared.connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .flatMap { $0.windows }
            .first { $0.isKeyWindow }

        return window?.safeAreaInsets.bottom ?? 0
    }

    var safeBottom: CGFloat {
        let window = UIApplication.shared.connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .flatMap { $0.windows }
            .first { $0.isKeyWindow }

        return window?.safeAreaInsets.bottom ?? 0
    }
}

