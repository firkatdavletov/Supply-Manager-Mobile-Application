//
//  VerificationView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct VerificationView: View {
    let component: VerificationComponent
    
    @StateValue private var state: VerifyViewState
    
    init(component: VerificationComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    
    var body: some View {
        switch state.authType {
        case "sms" : VerifyBySmsContent(
            phoneNumber: state.phoneNumber,
            isLoading: state.isLoading,
            onCodeChanged: { newValue in
                component.onEvent(event: VerifyViewEventOnCodeChanged(newValue: newValue))
            },
            onBack: {
                component.onEvent(event: VerifyViewEventOnBackClicked())
            }
        );
        case "call" : VerifyByCallContent(
            callPhone: state.callPhone!,
            isLoading: state.isLoading,
            onCallPhoneClicked: {
                component.onEvent(event: VerifyViewEventOnCallPhoneClicked())
            },
            onBack: {
                component.onEvent(event: VerifyViewEventOnBackClicked())
            },
            onAppBecameActive: {
                component.onEvent(event: VerifyViewEventOnAppBecameActive())
            }
        )
        default:
            EmptyView()
        }
    }
}
