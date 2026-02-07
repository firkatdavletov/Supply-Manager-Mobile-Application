//
//  SignInView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Combine
import Shared


struct SignInView: View {
    let component: SignInComponent
    
    @StateValue private var state: SignInViewState
    
    init(component: SignInComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    
    var body: some View {
        SignInContent(
            onPhoneNumberEntered: { phone in
                component.onEvent(event: SignInViewEventOnPhoneNumberChanged(phone: phone))
            },
            isLoading: state.isLoading,
            authTypes: state.authTypes) { type in
                component.onEvent(event: SignInViewEventAuthTypeClicked(authType: type))
            } onLoginButtonClicked: { value in
                component.onEvent(event: SignInViewEventLoginClicked(phoneNumber: value))
            } onBackClicked: {
                component.onEvent(event: SignInViewEventOnBackClicked())
            }
            .navigationBarBackButtonHidden()
    }
}
