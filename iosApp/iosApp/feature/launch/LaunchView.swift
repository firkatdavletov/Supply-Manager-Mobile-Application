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
        ZStack {
            Image("logo")
                .frame(width: 10, height: 10, alignment: .center)
            
            VStack(alignment: .trailing, spacing: 16) {
                Spacer()
                if (state.isLoading) {
                    ProgressView()
                } else if (state.isError) {
                    VStack {
                        ConfirmButton(
                            title: "Повторить",
                            onConfirm: {
                                component.onEvent(event: LaunchViewEventOnReconnect())
                            },
                            isLoading: false,
                            isDisabled: false
                        )
                        Text(
                            state.errorMessages.first!
                        )
                        .foregroundColor(Color("error"))
                        .font(.system(size: 12, weight: .light, design: .rounded))
                    }
                }
            }
            .padding(.horizontal)
            .frame(maxWidth: .infinity, alignment: .bottom)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .toastBanner(
            isPresented: $showToast,
            message: "Что-то пошло не так",
            type: .error
        )
        .onAppear {
            effectObserver.start(component: component)
        }
        .onDisappear {
            effectObserver.stop()
        }
        .onReceive(effectObserver.$effect.dropFirst()) { effect in
            guard let effect = effect else { return }
        
            switch effect {
            case is LaunchViewEffectShowError:
                showToast = true
                toastMessage = (effect as! LaunchViewEffectShowError).message
            default:
                break
            }
        }
    }
}
