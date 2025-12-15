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
            RemoteImage(
                urlString: imageUrl ?? "",
                height: 104,
                cornerRadius: 15
            )
                .clipShape(RoundedCorner(radius: 24))
                .shadow(color: Color("LightGrey"), radius: 4)
            
            Text(title)
                .font(AppTypography.bodyMedium)
                .foregroundColor(Color.onBackground)
            Spacer()
        }
    }
}

#Preview {
    CategoryCardView(title: "String", imageUrl: "")
}
