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
            login: state.login,
            password: state.password,
            onLoginChanged: { login in
                component.onEvent(event: SignInViewEventOnLoginChanged(login: login))
            },
            onPasswordChanged: { password in
                component.onEvent(event: SignInViewEventOnPasswordChanged(password: password))
            },
            onLoginClicked: {
                component.onEvent(event: SignInViewEventOnLoginClicked())
            },
            isLoading: state.isLoading,
            isLoginEnabled: state.confirmEnabled,
            showBackButton: true,
            onBackClicked: {
                component.onEvent(event: SignInViewEventOnBackClicked())
            }
        )
            .navigationBarBackButtonHidden()
    }
}
