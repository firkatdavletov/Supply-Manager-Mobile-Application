//
//  ToastBannerContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 21/11/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct ToastBannerContent: View {
    let message: String
    let type: ToastType
    
    var body: some View {
        HStack {
            Text(message)
                .foregroundColor(.white)
                .font(.system(size: 16, weight: .medium))
                .multilineTextAlignment(.leading)
            
            Spacer()
        }
        .padding()
        .frame(maxWidth: .infinity)
        .background(type.background)
        .cornerRadius(14)
        .padding(.horizontal, 16)
        .shadow(radius: 4)
    }
}
