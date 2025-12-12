//
//  ToastBanner.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 21/11/2025.
//  Copyright © 2025 orgName. All rights reserved.
//


import SwiftUI

struct ToastBanner: ViewModifier {
    @Binding var isPresented: Bool
    let message: String
    let type: ToastType
    let duration: TimeInterval
    
    func body(content: Content) -> some View {
        ZStack {
            content
            
            if isPresented {
                VStack {
                    ToastBannerContent(message: message, type: type)
                        .transition(.move(edge: .top).combined(with: .opacity))
                        .onAppear {
                            DispatchQueue.main.asyncAfter(deadline: .now() + duration) {
                                withAnimation {
                                    isPresented = false
                                }
                            }
                        }
                    Spacer()
                }
                .zIndex(999)
                .animation(.spring(), value: isPresented)
            }
        }
    }
}