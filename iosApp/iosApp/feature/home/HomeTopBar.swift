//
//  HomeTopBar.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 28/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import SwiftUI

struct HomeTopBar: View {

    let companyLogoName: String
    let userName: String
    let onAddTap: () -> Void
    let onProfileClicked: () -> Void

    var body: some View {
        HStack(spacing: 12) {

            Image(companyLogoName)
                .resizable()
                .scaledToFit()
                .frame(width: 36, height: 36)
                .clipShape(RoundedRectangle(cornerRadius: 8))

            VStack(alignment: .leading, spacing: 2) {
                Text("Добро пожаловать")
                    .font(.system(size: 12))
                    .foregroundColor(.gray)

                Text(userName)
                    .font(.system(size: 16, weight: .semibold))
                    .foregroundColor(.black)
            }
            .onTapGesture {
                onProfileClicked()
            }

            Spacer()

            Button(action: onAddTap) {
                Image(systemName: "gear")
                    .foregroundColor(Color.blue)
            }
        }
        .padding(.horizontal)
        .padding(.vertical, 10)
        .background(Color.white)
        .overlay(
            Rectangle()
                .fill(Color.gray.opacity(0.2))
                .frame(height: 1),
            alignment: .bottom
        )
    }
}
