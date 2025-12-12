//
//  toast_banner_ext.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 21/11/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

extension View {
    func toastBanner(
        isPresented: Binding<Bool>,
        message: String,
        type: ToastType = .info,
        duration: TimeInterval = 2.0
    ) -> some View {
        self.modifier(
            ToastBanner(
                isPresented: isPresented,
                message: message,
                type: type,
                duration: duration
            )
        )
    }
}
