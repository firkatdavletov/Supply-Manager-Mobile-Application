//
//  ToastType.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 21/11/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

enum ToastType {
    case success
    case error
    case info

    var background: Color {
        switch self {
        case .success: return Color.green.opacity(0.9)
        case .error:   return Color.red.opacity(0.9)
        case .info:    return Color.blue.opacity(0.9)
        }
    }
    
    var icon: Image {
        switch self {
        case .success: return Image(systemName: "checkmark.circle.fill")
        case .error:   return Image(systemName: "xmark.octagon.fill")
        case .info:    return Image(systemName: "info.circle.fill")
        }
    }
}
