//
//  ConfirmButton.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 20/06/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct ConfirmButton: View {
    let title: String
    let onConfirm: (() -> Void)?
    let isLoading: Bool
    let isDisabled: Bool

    var body: some View {
        Button(action: {
            if !isLoading {
                onConfirm?()
            }
        }) {
            ZStack {
                if isLoading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: .white))
                } else {
                    Text(title)
                        .foregroundColor(.white)
                        .font(.system(size: 14, weight: .bold, design: .rounded))
                }
            }
            .frame(maxWidth: .infinity)
            .padding()
            .background(isDisabled ? Color.gray : Color("PrimaryColor"))
            .cornerRadius(25)
        }
        .disabled(isDisabled || isLoading)
    }
}
