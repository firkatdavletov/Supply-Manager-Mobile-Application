//
//  AISearchContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 21/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct AISearchContent: View {
    let value: String = ""
    let placeholder: String = "Search"
    let onChange: (String) -> Void = { _ in }
    
    var body: some View {
        StyledTextField(
            value: value,
            placeholder: placeholder,
            isError: false,
            onChange: onChange
        )
        .padding(.horizontal)
    }
}

#Preview {
    AISearchContent()
}
