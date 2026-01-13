//
//  MapContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 27/10/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct MapContent: View {
    let addressString: String?
    let deliveryInfo: String?
    let isLoading: Bool
    let isSearching: Bool
    let isConfirmEnabled: Bool
    let moveToLocation: Bool
    let deliveryType: DeliveryType
    let departments: Array<DepartmentModel>
    let selectedDepartment: KotlinInt?
    let location: UiPoint?
    let onSelectDeliveryType: (DeliveryType) -> Void
    let onConfirm: () -> Void
    let onBackButtonClicked: () -> Void
    let onMapMoved: (Double, Double, UInt, Bool) -> Void
    let onSelectDepartment: (Int64) -> Void
    let onRequestLocation: () -> Void
    let showBackButton: Bool
    let showSearchButton: Bool
    let showMap: Bool
    
    var body: some View {
        ZStack {
            if (showMap) {
                YandexMapView(
                    position: location,
                    moveToLocation: moveToLocation,
                    showDepartments: deliveryType == DeliveryType.pickup,
                    selectedDepartment: selectedDepartment,
                    departments: departments,
                    deliveryType: deliveryType,
                    onMapMoved: onMapMoved,
                    onSelectDepartments: onSelectDepartment
                )
                .edgesIgnoringSafeArea(.all)
            }
            VStack {
                HStack {
                    if (showBackButton) {
                        IconButton(
                            systemName: "arrow.backward",
                            tint: Color.secondaryContainer,
                            foreground: Color.onSecondaryContainer,
                            action: onBackButtonClicked
                        )
                    }
                    Spacer()
                    IconButton(
                        systemName: "magnifyingglass",
                        tint: Color.secondaryContainer,
                        foreground: Color.onSecondaryContainer,
                        action: onBackButtonClicked
                    )
                }
                
                if (addressString != nil) {
                    Text(addressString!)
                        .font(AppTypography.headlineLarge)
                        .foregroundStyle(Color.black)
                        .multilineTextAlignment(.center)
                }
                if (deliveryInfo != nil) {
                    Text(deliveryInfo!)
                        .font(AppTypography.bodyMedium)
                        .padding(.horizontal, 16)
                        .padding(.vertical, 8)
                        .background(
                            RoundedRectangle(cornerRadius: 16)
                                .fill(Color.secondaryContainer)
                        )
                        .foregroundStyle(Color.onBackground)
                }
                Spacer()
            }
            .padding(.horizontal)
            
            if (deliveryType == DeliveryType.delivery) {
                TooltipComponent()
            }
            
            VStack {
                Spacer()
                IconButton(
                    systemName: "location",
                    tint: Color.secondaryContainer,
                    foreground: Color.onSecondaryContainer,
                    action: onRequestLocation
                )
                    .frame(maxWidth: .infinity, alignment: .trailing)
                    .padding()
                    .shadow(color: .black.opacity(0.2), radius: 4, x: 0, y: 2)
                VStack {
                    HStack {
                        SelectedButton(
                            title: "Самовывоз",
                            selected: deliveryType == DeliveryType.pickup,
                            action: {
                                onSelectDeliveryType(DeliveryType.pickup)
                            }
                        )
                        SelectedButton(
                            title: "Доставка",
                            selected: deliveryType == DeliveryType.delivery,
                            action: {
                                onSelectDeliveryType(DeliveryType.delivery)
                            }
                        )
                    }
                    RoundedButton(
                        title: "Подтвердить",
                        onClick: onConfirm,
                        enabled: isConfirmEnabled
                    )
                    .disabled(!isConfirmEnabled)
                }
                .padding(.horizontal)
            }
        }
    }
}

#Preview {
    MapContent(
        addressString: "ул. Косоротова, 56",
        deliveryInfo: "Доставка 100 ₽",
        isLoading: false,
        isSearching: false,
        isConfirmEnabled: true,
        moveToLocation: false,
        deliveryType: DeliveryType.delivery,
        departments: [],
        selectedDepartment: KotlinInt(int: 0),
        location: nil,
        onSelectDeliveryType: { _ in },
        onConfirm: {},
        onBackButtonClicked: {},
        onMapMoved: {_,_,_,_ in },
        onSelectDepartment: {_ in },
        onRequestLocation: {},
        showBackButton: true,
        showSearchButton: true,
        showMap: true
    )
}
