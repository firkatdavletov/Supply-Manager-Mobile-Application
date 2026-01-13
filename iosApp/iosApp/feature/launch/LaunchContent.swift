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
                    RoundedButton(
                        title: "Повторить",
                        onClick: onRetryClicked,
                        enabled: true
                    )
                }
            }
            .padding(16)
            .padding(.bottom, safeAreaBottomPadding()) // аналог navigationBarsPadding()
        }
    }

    /// Для корректной поддержки safe area, аналог navigationBarsPadding()
    private func safeAreaBottomPadding() -> CGFloat {
        let window = UIApplication.shared.connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .flatMap { $0.windows }
            .first { $0.isKeyWindow }

        return window?.safeAreaInsets.bottom ?? 0
    }
}




