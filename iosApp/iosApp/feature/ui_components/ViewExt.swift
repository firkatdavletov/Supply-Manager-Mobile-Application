//
//  ViewExt.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 20.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

extension View {
    @ViewBuilder func toast<T, A>(
        item: T?,
        onDismiss: @escaping (T) -> Void,
        message: (T) -> Text,
    ) -> some View where A : View {
        ZStack {
            self
            
            if let item = item {
                VStack {
                    Spacer()
                    ToastView(message: message)
                        .transition(.move(edge: .bottom).combined(with: .opacity))
                        .animation(.easeInOut, value: isPresented.wrappedValue)
                        .onAppear {
                            DispatchQueue.main.asyncAfter(deadline: .now() + duration) {
                                onDismiss()
                            }
                        }
                        .padding(.bottom, 40)
                }
            }
        }
    }
    

    statuc func hideKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder),
                                        to: nil, from: nil, for: nil)
    }
}

extension View {
    func elevation(_ level: CGFloat) -> some View {
        self
            .shadow(
                color: Color.black.opacity(0.12),
                radius: level,
                x: 0,
                y: level / 2
            )
    }
}

