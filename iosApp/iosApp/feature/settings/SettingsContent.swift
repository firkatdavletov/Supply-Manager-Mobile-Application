//
//  SettingsContent.swift
//  iosApp
//
//  Created by Codex on 21/02/2026.
//

import SwiftUI
import Shared

struct SettingsContent: View {
    let title: String
    let items: [SettingsMenuItem]
    let selectedItem: SettingsMenuItem?
    let onBack: () -> Void
    let onItemClicked: (SettingsMenuItem) -> Void

    var body: some View {
        VStack(spacing: 0) {
            topBar

            ScrollView {
                VStack(spacing: 0) {
                    ForEach(Array(items.enumerated()), id: \.offset) { index, item in
                        let isSelected = selectedItem?.title == item.title

                        Button(
                            action: { onItemClicked(item) },
                            label: {
                                HStack(spacing: 12) {
                                    Text(item.title)
                                        .font(AppTypography.bodyLarge)
                                        .foregroundStyle(Color.onBackground)
                                    Spacer()
                                    Image(systemName: "chevron.right")
                                        .font(.system(size: 14, weight: .semibold))
                                        .foregroundStyle(Color.onSurface)
                                }
                                .frame(maxWidth: .infinity)
                                .padding(.horizontal, 16)
                                .padding(.vertical, 14)
                                .background(
                                    isSelected
                                        ? Color.blue.opacity(0.35)
                                        : Color.surface
                                )
                            }
                        )
                        .buttonStyle(.plain)

                        if index < items.count - 1 {
                            Divider()
                                .overlay(Color.onSurface.opacity(0.15))
                                .padding(.leading, 16)
                        }
                    }
                }
                .clipShape(RoundedRectangle(cornerRadius: 16))
                .padding(16)
            }
        }
        .background(Color.background.ignoresSafeArea())
    }
}

extension SettingsContent {
    private var topBar: some View {
        HStack {
            Button(action: onBack) {
                Image(systemName: "chevron.left")
                    .foregroundColor(Color.white)
                    .frame(width: 28, height: 28)
            }
            Spacer()
            Text(title)
                .font(AppTypography.titleLarge)
                .foregroundStyle(Color.white)
            Spacer()
            Color.clear
                .frame(width: 28, height: 28)
        }
        .padding(.horizontal, 16)
        .padding(.top, 8)
        .padding(.bottom, 12)
        .background(Color.blue.ignoresSafeArea(edges: .top))
    }
}
