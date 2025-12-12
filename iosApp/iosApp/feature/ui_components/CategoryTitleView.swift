//
//  CategoryTitleView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 17/06/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct CategoryTitleView: View {
    let title: String

    var body: some View {
        Text(title)
            .font(.system(size: 20, weight: .regular, design: .rounded))
            .foregroundColor(Color("DarkGrayColor"))
            .padding(.horizontal, 24)
            .padding(.vertical, 12)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(
                TopLeftRightRoundedBorder(cornerRadius: 24)
                    .stroke(Color("PrimaryColor"), lineWidth: 1)
                    .background(
                        Color.white
                            .clipShape(TopLeftRightRoundedBorder(cornerRadius: 24))
                    )
            )
    }
}
