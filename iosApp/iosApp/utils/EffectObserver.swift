//
//  EffectObserver.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 21/11/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import Shared

class EffectObserver : ObservableObject {
    @Published var effect: String? = nil

    private var disposer: (() -> Void)? = nil

    func start(component: IosComponent) {
            disposer = component.observeEvents { event in
                DispatchQueue.main.async {
                    self.effect = event
                }
            }
        }

        func stop() {
            disposer?()
            disposer = nil
        }
}
