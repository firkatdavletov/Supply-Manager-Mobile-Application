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
    let storeIsClosed: Bool

    let onChangeDeliveryType: (DeliveryType) -> Void
    let onBackButtonClicked: () -> Void
    let onSelectAddress: () -> Void
    let onConfirmClicked: () -> Void
    let onIsPrivateHouseChanged: (Bool) -> Void
    let onEntranceChanged: (String) -> Void
    let onFlatChanged: (String) -> Void
    let onCommentChanged: (String) -> Void

    var body: some View {
        VStack {
            header
            ScrollView {
                if (storeIsClosed) {
                    storeIsClosedView
                }
                if (deliveryType == DeliveryType.delivery) {
                    deliverySection
                } else {
                    pickupSection
                }
                paymentSection
                bottomSummary
            }
            PrimaryButton(
                title: "Заказать",
                onClick: onConfirmClicked,
                enabled: deliveryType != .delivery || addressString != nil
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
        .background(Color.primaryContainer)
    }
}

extension PaymentContent {
    private var storeIsClosedView: some View {
        Text("Ресторан закрыт")
            .font(AppTypography.headlineSmall)
            .foregroundStyle(Color.onBackground)
            .frame(maxWidth: .infinity, alignment: .center)
    }
}

extension PaymentContent {
    private var pickupSection: some View {
        VStack(alignment: .leading, spacing: 8) {

            Text("Откуда")
                .font(AppTypography.headlineSmall)

            if let department = departmentName {
                Text(department)
                    .font(AppTypography.titleLarge)
                    .foregroundStyle(Color.onBackground)
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
    private var deliverySection: some View {
        VStack(alignment: .leading, spacing: 8) {

            Text("Куда")
                .font(AppTypography.headlineSmall)

            if let department = departmentName {
                Text(addressString ?? "Ошибка загрузки адреса")
                    .font(AppTypography.titleLarge)
                    .foregroundStyle(Color.onBackground)
            }
            
            SelectedButton(
                title: "Частный дом",
                selected: isPrivateHome) {
                    onIsPrivateHouseChanged(!isPrivateHome)
                }
            if (!isPrivateHome) {
                HStack {
                    StyledTextField(
                        value: entrance,
                        placeholder: "Подъезд",
                        isError: entranceInputError != nil,
                        onChange: onEntranceChanged
                    )
                    .keyboardType(.phonePad)
                    .padding(.vertical, 8)
                    StyledTextField(
                        value: flat,
                        placeholder: "Квартира",
                        isError: flatInputError != nil,
                        onChange: onFlatChanged
                    )
                    .keyboardType(.phonePad)
                    .padding(.vertical, 8)
                }
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
    private var paymentSection: some View {
        VStack(alignment: .leading) {

            Text("Оплата")
                .font(.title2)
                .padding(.bottom, 16)

            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 8) {
                    ForEach(paymentTypes, id: \.id) { type in
                        SelectedButton(
                            title: type.title,
                            selected: true,
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
    private var bottomSummary: some View {
        VStack(spacing: 12) {

            HStack {
                Text("Стоимость продуктов:")
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(Color.onBackground)
                Spacer()
                Text("\(productPrice) руб")
                    .font(AppTypography.titleLarge)
                    .foregroundStyle(Color.onBackground)
            }
            .font(.title3)

            if deliveryType == .delivery {
                HStack {
                    Text("Стоимость доставки:")
                        .font(AppTypography.bodyLarge)
                        .foregroundStyle(Color.onBackground)
                    Spacer()
                    Text(deliveryPrice == 0 ? "бесплатно" : "\(deliveryPrice) руб")
                        .font(AppTypography.titleLarge)
                        .foregroundStyle(Color.onBackground)
                }
            }

            HStack {
                Text("Итого:")
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(Color.onBackground)
                Spacer()
                Text("\(totalAmount) руб")
                    .font(AppTypography.titleLarge)
                    .foregroundStyle(Color.onBackground)
            }
        }
        .padding(.horizontal, 16)
    }
}

#Preview {
    PaymentContent(
        deliveryType: DeliveryType.delivery,
        addressString: "ул. Щербакова 150/2",
        departmentName: "Точисского 20",
        isPrivateHome: false,
        entrance: "2",
        entranceInputError: nil,
        flat: "20",
        flatInputError: nil,
        comment: "",
        totalAmount: 1699,
        deliveryPrice: 100,
        productPrice: 1599,
        paymentTypes: [
            PaymentTypeModel(id: "1", title: "Cash", selected: true)
        ],
        storeIsClosed: false,
        onChangeDeliveryType: { deliveryType in
            
        },
        onBackButtonClicked: {
           
        },
        onSelectAddress: {
      
        },
        onConfirmClicked: {
            
        },
        onIsPrivateHouseChanged: { value in
          
        },
        onEntranceChanged: { entrance in
      
        },
        onFlatChanged: { flat in
        
        },
        onCommentChanged: { comment in
          
        }
    )
}
