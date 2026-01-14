//
//  AppTypography.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 27/10/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

enum AppTypography {

    static let displayLarge = Font.variable(
        name: "Roboto-Bold",
        size: 57,
        weight: VFConfig.DisplayLarge.weight,
        width: VFConfig.DisplayLarge.width,
        slant: VFConfig.DisplayLarge.slant
    )

    static let displayMedium = Font.variable(
        name: "Roboto-Bold",
        size: 45,
        weight: VFConfig.DisplayMedium.weight,
        width: VFConfig.DisplayMedium.width,
        slant: VFConfig.DisplayMedium.slant
    )

    static let displaySmall = Font.variable(
        name: "Roboto-Bold",
        size: 36,
        weight: VFConfig.DisplaySmall.weight,
        width: VFConfig.DisplaySmall.width,
        slant: VFConfig.DisplaySmall.slant
    )

    static let headlineLarge = Font.variable(
        name: "Roboto-SemiBold",
        size: 32,
        weight: VFConfig.HeadlineLarge.weight,
        width: VFConfig.HeadlineLarge.width,
        slant: VFConfig.HeadlineLarge.slant
    )

    static let headlineMedium = Font.variable(
        name: "Roboto-SemiBold",
        size: 28,
        weight: VFConfig.HeadlineMedium.weight,
        width: VFConfig.HeadlineMedium.width,
        slant: VFConfig.HeadlineMedium.slant
    )

    static let headlineSmall = Font.variable(
        name: "Roboto-SemiBold",
        size: 24,
        weight: VFConfig.HeadlineSmall.weight,
        width: VFConfig.HeadlineSmall.width,
        slant: VFConfig.HeadlineSmall.slant
    )

    static let titleLarge = Font.variable(
        name: "Roboto-Medium",
        size: 22,
        weight: VFConfig.TitleLarge.weight,
        width: VFConfig.TitleLarge.width,
        slant: VFConfig.TitleLarge.slant
    )

    static let titleMedium = Font.variable(
        name: "Roboto-Medium",
        size: 16,
        weight: VFConfig.TitleMedium.weight,
        width: VFConfig.TitleMedium.width,
        slant: VFConfig.TitleMedium.slant
    )

    static let titleSmall = Font.variable(
        name: "Roboto-Medium",
        size: 14,
        weight: VFConfig.TitleSmall.weight,
        width: VFConfig.TitleSmall.width,
        slant: VFConfig.TitleSmall.slant
    )

    static let bodyLarge = Font.variable(
        name: "Roboto-Regular",
        size: 16,
        weight: VFConfig.BodyLarge.weight,
        width: VFConfig.BodyLarge.width,
        slant: VFConfig.BodyLarge.slant
    )

    static let bodyMedium = Font.variable(
        name: "Roboto-Regular",
        size: 14,
        weight: VFConfig.BodyMedium.weight,
        width: VFConfig.BodyMedium.width,
        slant: VFConfig.BodyMedium.slant
    )

    static let bodySmall = Font.variable(
        name: "Roboto-Regular",
        size: 12,
        weight: VFConfig.BodySmall.weight,
        width: VFConfig.BodySmall.width,
        slant: VFConfig.BodySmall.slant
    )

    static let labelLarge = Font.variable(
        name: "Roboto-Thin",
        size: 14,
        weight: VFConfig.LabelLarge.weight,
        width: VFConfig.LabelLarge.width,
        slant: VFConfig.LabelLarge.slant
    )

    static let labelMedium = Font.variable(
        name: "Roboto-Thin",
        size: 12,
        weight: VFConfig.LabelMedium.weight,
        width: VFConfig.LabelMedium.width,
        slant: VFConfig.LabelMedium.slant
    )

    static let labelSmall = Font.variable(
        name: "Roboto-Thin",
        size: 11,
        weight: VFConfig.LabelSmall.weight,
        width: VFConfig.LabelSmall.width,
        slant: VFConfig.LabelSmall.slant
    )
}

extension Font {

    static func variable(
        name: String,
        size: CGFloat,
        weight: Double,
        width: Double,
        slant: Double
    ) -> Font {

        let variationSettings: [String: Any] = [
            "wght": weight,
            "wdth": width,
            "slnt": slant
        ]

        let attributes: [UIFontDescriptor.AttributeName: Any] = [
            .name: name,
            .size: size,
            UIFontDescriptor.AttributeName(rawValue: kCTFontVariationAttribute as String): variationSettings
        ]

        let descriptor = UIFontDescriptor(fontAttributes: attributes)
        let uiFont = UIFont(descriptor: descriptor, size: size)

        return Font(uiFont)
    }
}
