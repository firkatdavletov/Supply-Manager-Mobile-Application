//
//  MapView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared
import CoreLocation

struct MapView: View {
    let component: DefaultMapComponent
    @StateObject private var locationManager = LocationManager()
    
    @StateValue private var state: MapViewState
    
    init(component: MapComponent) {
        self.component = component as! DefaultMapComponent
        _state = StateValue(self.component.state)
    }
    
    var body: some View {
        MapContent(
            addressString: state.deliveryAddress,
            deliveryInfo: state.deliveryInfo,
            isLoading: state.isLoading,
            isSearching: state.isSearching,
            isConfirmEnabled: state.confirmEnabled,
            moveToLocation: state.showLocation,
            deliveryType: state.deliveryType,
            departments: state.departments,
            selectedDepartment: state.selectedDepartment,
            location: state.currentPosition,
            onSelectDeliveryType: { type in
                component.onEvent(event: MapViewEventOnChangeDeliveryType(type: type))
            },
            onConfirm: {
                component.onEvent(event: MapViewEventOnConfirm())
            },
            onBackButtonClicked: {
                component.onEvent(event: MapViewEventOnBackClicked())
            },
            onMapMoved: { lat, lng, reason, finished in
                component.onEvent(event: MapViewEventOnMapMoved(latitude: lat, longitude: lng, reason: Int32(reason), finished: finished))
            },
            onSelectDepartment: { id in
                component.onEvent(event: MapViewEventOnDepartmentSelected(id: Int32(truncating: id)))
            },
            onRequestLocation: {
                locationManager.requestLocation { location in
                    component.onEvent(event: MapViewEventOnMoveToLocation(latitude: location.coordinate.latitude, longitude: location.coordinate.longitude))
                }
            },
            showBackButton: true,
            showMap: true
        )
        .navigationBarBackButtonHidden(true)
        .onAppear {
            locationManager.requestLocation { location in
                component.onEvent(event: MapViewEventOnMoveToLocation(latitude: location.coordinate.latitude, longitude: location.coordinate.longitude))
            }
        }
    }
}

