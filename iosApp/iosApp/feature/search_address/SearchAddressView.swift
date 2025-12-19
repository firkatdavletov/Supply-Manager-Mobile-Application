//
//  SearchAddressView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 26/07/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct SearchAddressView: View {
    let component: SearchAddressComponent
    @StateValue var state: SearchAddressViewState
    @State var query: String = ""
    @FocusState private var isTextFieldFocused: Bool
    
    init(component: SearchAddressComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    
    var body: some View {
        SearchAddressContent(
            query: state.query,
            addresses: state.addresses) { input in
                component.onEvent(event: SearchAddressViewEventOnQueryChanged(query: input))
            } onBackButtonClicked: {
                component.onEvent(event: SearchAddressViewEventOnBackClicked())
            } onMapButtonClicked: {
                component.onEvent(event: SearchAddressViewEventOnMapClicked())
            } onAddressClicked: { geoAddressModel in
                component.onEvent(event: SearchAddressViewEventOnAddressClicked(address: geoAddressModel))
            }
            .navigationBarBackButtonHidden(true)
    }
}
