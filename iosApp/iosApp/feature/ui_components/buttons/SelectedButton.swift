//
//  SelectedButton.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 12/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct SelectedButton: View {
    let title: String
    let selected: Bool
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            Text(title)
                .frame(maxWidth: .infinity)
                .padding(.vertical, 10)
                .padding(.horizontal, 16)
                .padding(2)
                .background(selected ? Color.primaryContainer : Color.secondaryContainer)
                .foregroundColor(selected ? .onPrimaryContainer : .onSecondaryContainer)
                .cornerRadius(25)
                .cornerRadius(25)
        }
    }
}

#Preview {
    SelectedButton(title: "Test", selected: false) {
        
    }
}
