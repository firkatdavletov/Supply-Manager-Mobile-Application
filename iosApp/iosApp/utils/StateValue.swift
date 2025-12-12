//
//  StateValue.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 22.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

@propertyWrapper struct StateValue<T : AnyObject>: DynamicProperty {
    @ObservedObject
    private var obj: ObservableValue<T>

    var wrappedValue: T { obj.value }

    init(_ value: Value<T>) {
        obj = ObservableValue(value)
    }
}
