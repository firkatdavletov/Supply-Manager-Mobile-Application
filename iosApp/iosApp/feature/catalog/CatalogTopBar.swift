//
//  HomeTopBar.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 28/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import SwiftUI

struct CatalogTopBar: View {
    let title: String
    let onBack: () -> Void
    var body: some View {
        HStack(spacing: 12) {
            
            Button(action: onBack) {
                Image(systemName: "arrow.left")
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(.white)
                    .frame(width: 40, height: 40)
                    .background(Color.blue)
                    .clipShape(Circle())
            }
            
            VStack(alignment: .leading, spacing: 2) {
                Text("Каталог")
                    .font(.system(size: 12))
                    .foregroundColor(.gray)
                
                Text(title)
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
