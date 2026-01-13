//
//  ProductImageView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 05/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//
import SwiftUI

struct ProductImageView: View {

    let imageUrl: String?

    var body: some View {
        AsyncImage(url: imageUrl.flatMap(URL.init)) { phase in
            switch phase {
            case .success(let image):
                image
                    .resizable()
                    .scaledToFill()

            case .failure:
                placeholder

            case .empty:
                placeholder

            @unknown default:
                placeholder
            }
        }
        .frame(height: 260)
        .clipped()
    }

    private var placeholder: some View {
        ZStack {
            Color.gray.opacity(0.2)
            Image(systemName: "photo")
                .font(.largeTitle)
                .foregroundColor(.gray)
        }
    }
}
