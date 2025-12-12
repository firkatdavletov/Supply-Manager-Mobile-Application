//
//  RootHolder.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 21.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

class RootHolder: ObservableObject {
    let lifeCycle: LifecycleRegistry
    let root: RootComponent
    
    init() {
        lifeCycle = LifecycleRegistryKt.LifecycleRegistry()
        root = DefaultRootComponent(
            componentContext: DefaultComponentContext(lifecycle: lifeCycle)
        )
    }
    
    deinit {
        LifecycleRegistryExtKt.destroy(lifeCycle)
    }
}
