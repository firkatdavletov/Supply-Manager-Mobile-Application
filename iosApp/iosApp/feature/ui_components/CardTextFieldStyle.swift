//
//  CardTextFieldStyle.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 21/07/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct CardTextFieldStyle: ViewModifier {
    func body(content: Content) -> some View {
        content
            .padding(.horizontal)
            .keyboardType(.phonePad)
            .frame(height: 62)
            .font(.system(size: 14, weight: .regular, design: .rounded))
            .foregroundColor(Color("DarkGrayColor"))
            .background(Color("IceBlue"))
            .cornerRadius(10)
            .lineLimit(1)
    }
}

extension View {
    func cardTextFieldStyle() -> some View {
        self.modifier(CardTextFieldStyle())
    }
}
