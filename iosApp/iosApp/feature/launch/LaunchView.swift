//
//  LaunchView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 07.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct LaunchView: View {
    let component: LaunchComponent
    
    @StateValue private var state: LaunchViewState
    @StateObject private var effectObserver = EffectObserver()
    @State private var showToast = false
    @State private var toastMessage = ""
    
    init(component: LaunchComponent) {
        self.component = component
        _state = StateValue((component as! DefaultLaunchComponent).state)
    }
    var body: some View {
        LaunchContent(
            isLoading: state.isLoading,
            isError: state.isError,
            onRetryClicked: {
                component.onEvent(event: LaunchViewEventOnReconnect())
            }
        )
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
