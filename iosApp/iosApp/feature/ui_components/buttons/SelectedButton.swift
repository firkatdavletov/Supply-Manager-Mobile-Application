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
        if (selected) {
            PrimaryButton(title: title, onClick: action, enabled: true)
        } else {
            SecondaryButton(title: title, onClick: action, enabled: true)
        }
    }
}

#Preview {
    SelectedButton(title: "Test", selected: false) {
        
    }
}
