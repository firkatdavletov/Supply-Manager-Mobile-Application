//
// Created by Фиркат Давлетов on 12/12/2025.
// Copyright (c) 2025 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct LaunchContent: View {
    let isLoading: Bool
    let isError: Bool
    let onRetryClicked: () -> Void

    var body: some View {
        ZStack {
            // Center circle with image
            ZStack {
                Circle()
                    .fill(Color.white)
                    .frame(width: 196, height: 196)

                Image.logo
                    .resizable()
                    .scaledToFit()
                    .padding(.horizontal, 16)
                    .frame(width: 196, height: 196)
                    .clipShape(Circle())
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)

            // Bottom section
            VStack {
                Spacer()
                if isLoading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle())
                        .frame(width: 32, height: 32)
                } else if isError {
                    PrimaryButton(
                        title: "Повторить",
                        onClick: onRetryClicked,
                        enabled: true
                    )
                }
            }
            .padding(16)
        }
    }
}




