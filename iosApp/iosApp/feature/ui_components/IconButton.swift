//
//  IconButton.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 27/10/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI


struct IconButton: View {
    let systemName: String
    let tint: Color
    let foreground: Color
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            Image(systemName: systemName)
                .font(.title2)
                .foregroundStyle(foreground)
                .padding()
                .background(Circle().fill(tint))
        }
    }
}

#Preview {
    IconButton(systemName: "trash", tint: .red, foreground: .white) {
        print("Удалить")
    }
}
