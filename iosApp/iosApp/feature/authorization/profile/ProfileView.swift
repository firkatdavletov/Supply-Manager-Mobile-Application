//
//  ProfileView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 16/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared
struct ProfileView: View {
    let component: ProfileComponent
    
    @StateValue private var state: ProfileViewState
    
    init(component: ProfileComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    var body: some View {
        ProfileContent(
            name: state.name,
            phone: state.phone,
            isLoading: state.isLoading
        ) { name in
            component.onEvent(event: ProfileViewEventOnNameChanged(name: name))
            } onSave: {
                component.onEvent(event: ProfileViewEventOnSave())
            } onDelete: {
                component.onEvent(event: ProfileViewEventOnDelete())
            } onLogout: {
                component.onEvent(event: ProfileViewEventOnLogout())
            } onBack: {
                component.onEvent(event: ProfileViewEventOnBackClicked())
            }
            .navigationBarBackButtonHidden(true)

    }
}
