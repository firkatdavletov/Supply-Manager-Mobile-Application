//
//  TooltipComponent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 27/10/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct TooltipComponent: View {
    var body: some View {
        VStack {
            Image("markerPoint")
                .resizable()
                .frame(width: 24, height: 32)
            Image("markerEllipse")
                .resizable()
                .frame(width: 18, height: 6)
        }
    }
}

#Preview {
    TooltipComponent()
}
