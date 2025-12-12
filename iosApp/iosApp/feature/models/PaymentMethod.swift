//
//  PaymentMethod.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 09/07/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import Foundation

struct PaymentMethod: Identifiable, Equatable {
    let id = UUID()
    let name: String
    let iconName: String
}
