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
    let onSelectDepartment: (KotlinInt) -> Void
    let onRequestLocation: () -> Void
    let showBackButton: Bool
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
                    onMapMoved: onMapMoved,
                )
                .edgesIgnoringSafeArea(.all)
            }
            VStack {
                HStack {
                    if (showBackButton) {
                        IconButton(
                            systemName: "arrow.backward",
                            tint: Color("secondaryContainer"),
                            foreground: Color("onSecondaryContainer"),
                            action: onBackButtonClicked
                        )
                    }
                    Spacer()
                    IconButton(
                        systemName: "magnifyingglass",
                        tint: Color("secondaryContainer"),
                        foreground: Color("onSecondaryContainer"),
                        action: onBackButtonClicked
                    )
                }
                
                if (addressString != nil) {
                    Text(addressString!)
                        .font(AppTypography.headlineLarge)
                }
                if (deliveryInfo != nil) {
                    Text(deliveryInfo!)
                        .font(AppTypography.bodyMedium)
                        .padding(.horizontal, 16)
                        .padding(.vertical, 8)
                        .background(
                            RoundedRectangle(cornerRadius: 16)
                                .fill(Color("secondaryContainer"))
                        )
                        .foregroundStyle(Color("onSecondaryContainer"))
                }
                Spacer()
            }
            .padding(.horizontal)
            
            if (deliveryType == DeliveryType.delivery) {
                TooltipComponent()
            }
            
            VStack {
                IconButton(
                    systemName: "location",
                    tint: Color("secondaryContainer"),
                    foreground: Color("onSecondaryContainer"),
                    action: onRequestLocation
                )
                    .frame(maxWidth: .infinity, alignment: .trailing)
                    .shadow(color: .black.opacity(0.2), radius: 4, x: 0, y: 2)
                HStack {
                    RoundedButton(
                        title: "Самовывоз",
                        onClick: {
                            onSelectDeliveryType(DeliveryType.pickup)
                        },
                        background: deliveryType == DeliveryType.pickup ? Color("primaryContainer") : Color("secondaryContainer"),
                        foreground: Color("onPrimaryContainer"),
                        enabled: true
                    )
                    RoundedButton(
                        title: "Доставка",
                        onClick: {
                            onSelectDeliveryType(DeliveryType.delivery)
                        },
                        background: deliveryType == DeliveryType.delivery ? Color("primaryContainer") : Color("secondaryContainer"),
                        foreground: Color("onPrimaryContainer"),
                        enabled: true
                    )
                }
                RoundedButton(
                    title: "Подтвердить",
                    onClick: {
                        onSelectDeliveryType(DeliveryType.pickup)
                    },
                    background: Color("secondaryContainer"),
                    foreground: Color("onPrimaryContainer"),
                    enabled: isConfirmEnabled
                )
            }
                .frame(maxHeight: .infinity, alignment: .bottom)
                .padding(.horizontal)
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
        showMap: true
    )
}
