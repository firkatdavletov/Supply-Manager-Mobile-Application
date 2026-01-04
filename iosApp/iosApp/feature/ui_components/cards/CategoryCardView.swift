//
//  CategoryCardView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 09.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct CategoryCardView: View {
    let title: String
    let imageUrl: String?

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            RemoteImage(urlString: imageUrl)
                .frame(height: 112)
                .clipShape(
                    RoundedRectangle(cornerRadius: 16, style: .continuous)
                )

            Text(title)
                .font(AppTypography.titleMedium.weight(.semibold))
                .foregroundColor(Color.onBackground)
            Spacer()
        }
    }
}

#Preview {
    CategoryCardView(title: "String", imageUrl: "")
        .frame(width: 200, height: 200)
}
