//
//  StyledTextField.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 12/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct StyledTextField: View {
    let value: String
    let placeholder: String
    let isError: Bool
    let onChange: (String) -> Void

    var body: some View {
        TextField(placeholder, text: Binding(
            get: { value },
            set: { onChange($0) }
        ))
        .padding()
        .frame(height: 56)
        .background(
            RoundedRectangle(cornerRadius: 50)
                .stroke(isError ? Color.red : Color.gray.opacity(0.4), lineWidth: 1)
        )
    }
}
