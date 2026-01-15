//
//  SearchAddressContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 19/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct SearchAddressContent: View {
    let query: String
    let addresses: [GeoAddressModel]
    let onQueryInput: (String) -> Void
    let onBackButtonClicked: () -> Void
    let onMapButtonClicked: () -> Void
    let onAddressClicked: (GeoAddressModel) -> Void
    
    @FocusState var focused: Bool?
    
    var body: some View {
        VStack {
            topBar
            
            ScrollView {
                LazyVStack(spacing: 16) {
                    ForEach(addresses, id: \.hashValue) { address in
                        FoundedAddress(
                            address: address.addressString,
                            deliveryInfo: address.deliveryInfo,
                            city: address.city.name
                        )
                        .onTapGesture {
                            onAddressClicked(address)
                        }
                    }
                }
                .padding()
            }
            
            PrimaryButton(
                title: "Выбрать на карте",
                onClick: onMapButtonClicked,
                enabled: true
            )
            .padding()
        }
    }
}

extension SearchAddressContent {
    var topBar: some View {
        HStack(spacing: 16) {
            Button(action: onBackButtonClicked) {
                Image(systemName: "chevron.left")
                    .foregroundColor(Color.onPrimaryContainer)
            }
            TextField(
                "",
                text: Binding(
                    get: { query },
                    set: { onQueryInput($0) }
                ),
                prompt: Text("Поиск")
                    .font(AppTypography.bodyMedium)
                    .foregroundColor(Color.onPrimaryContainer.opacity(0.7))
            )
            .font(AppTypography.bodyMedium)
            .foregroundStyle(Color.onPrimaryContainer)
            .padding()
            .frame(height: 50)
            .background(
                RoundedRectangle(cornerRadius: 50)
                    .stroke(Color.onPrimaryContainer, lineWidth: 1)
            )
            .focused($focused, equals: true)
            .onAppear {
                focused = true
            }
        }
        .padding()
        .background(Color.primaryContainer)
    }
}

#Preview {
    SearchAddressContent(
        query: "",
        addresses: [
            GeoAddressModel(
                city: CityModel(id: 1, name: "белорецк", latitude: 0.0,longitude: 0.0, subCities: []),
                street: "ул. Косоротова",
                house: "2",
                entrance: KotlinInt(int: 2),
                deliveryInfo: nil,
                deliveryTime: 20,
                latitude: 0.0,
                longitude: 0.0,
                uri: nil
            )
        ]
    ) { String in
            
    } onBackButtonClicked: {
            
    } onMapButtonClicked: {
        
    } onAddressClicked: { address in
        
    }

}

struct FoundedAddress : View {
    let address: String
    let deliveryInfo: DeliveryInfoModel?
    let city: String
    
    var body: some View {
        
        VStack(alignment: .leading) {
            let deliveryInfoStr = deliveryInfo == nil ? "" : "Доставка \(deliveryInfo!.deliveryPrice) руб"
            HStack {
                Text(address)
                    .font(AppTypography.bodyLarge)
                    .foregroundStyle(Color.onBackground)
                Spacer()
                Text(deliveryInfoStr)
                    .font(AppTypography.titleSmall)
                    .foregroundStyle(Color.onSurface)
            }
            Text(city)
                .font(AppTypography.titleSmall)
                .foregroundStyle(Color.onSurface)
        }
        .padding(16)
        .frame(maxWidth: .infinity)
        .background(Color.background)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(Color.primaryContainer, lineWidth: 0)
        )
    }
}
