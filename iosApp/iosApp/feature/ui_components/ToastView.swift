//
//  ToastView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 20.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//


import SwiftUI

struct ToastView: View {
    let message: String

    var body: some View {
        Text(message)
            .padding()
            .background(Color.black.opacity(0.8))
            .foregroundColor(.white)
            .cornerRadius(10)
            .padding(.horizontal, 20)
    }
}