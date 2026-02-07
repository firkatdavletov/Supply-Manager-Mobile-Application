//
//  HomeTopBar.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 28/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import SwiftUI

struct SignInTopBar: View {
    
    var body: some View {
        HStack(spacing: 12) {
            
            VStack(alignment: .leading, spacing: 2) {
                Text("Авторизация")
                    .font(.system(size: 12))
                    .foregroundColor(.gray)
                
                Text("Подтвердите номер телефона")
                    .font(.system(size: 16, weight: .semibold))
                    .foregroundColor(.black)
            }
            
            Spacer()
        }
        .padding(.horizontal)
        .background(Color.white)
        .padding(.vertical, 10)
        .overlay(
            Rectangle()
                .fill(Color.gray.opacity(0.2))
                .frame(height: 1),
            alignment: .bottom
        )
    }
}
