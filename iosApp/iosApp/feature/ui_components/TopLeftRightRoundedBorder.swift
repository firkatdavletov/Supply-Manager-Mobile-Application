//
//  TopLeftRightRoundedBorder.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 17/06/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI


struct TopLeftRightRoundedBorder: Shape {
    var cornerRadius: CGFloat

    func path(in rect: CGRect) -> Path {
        var path = Path()

//        let tl = CGSize(width: cornerRadius, height: cornerRadius)
//        let tr = CGSize(width: cornerRadius, height: cornerRadius)

        // start at bottom-left
        path.move(to: CGPoint(x: 0, y: rect.maxY))

        // up left side
        path.addLine(to: CGPoint(x: 0, y: rect.minY + cornerRadius))
        path.addQuadCurve(to: CGPoint(x: cornerRadius, y: rect.minY),
                          control: CGPoint(x: 0, y: rect.minY))

        // top side
        path.addLine(to: CGPoint(x: rect.maxX - cornerRadius, y: rect.minY))

        // top-right corner
        path.addQuadCurve(to: CGPoint(x: rect.maxX, y: rect.minY + cornerRadius),
                          control: CGPoint(x: rect.maxX, y: rect.minY))

        // down right side
        path.addLine(to: CGPoint(x: rect.maxX, y: rect.maxY))

        return path
    }
}
