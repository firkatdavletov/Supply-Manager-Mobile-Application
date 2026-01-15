//
//  VerificationContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 10/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import SwiftUI

struct VerifyByCallContent: View {
    let callPhone: String
    let isLoading: Bool
    let onCallPhoneClicked: () -> Void
    let onBack: () -> Void
    let onAppBecameActive: () -> Void
    
    @Environment(\.scenePhase) private var scenePhase
    
    var body: some View {
        VStack(spacing: 0) {
            Text("Подтверждение")
                .foregroundColor(.white)
                .font(.system(size: 30, weight: .bold, design: .rounded))
                .bold()
                .padding(.top, 132)
            
            Text("Для подтверждения позвоните по номеру \(callPhone)")
                .foregroundColor(.white)
                .font(.system(size: 16, weight: .regular, design: .rounded))
                .bold()
                .padding(.vertical, 16)
                .multilineTextAlignment(.center)
            
            VStack {
                Button(action: onBack) {
                    Text("Вернуться назад")
                        .foregroundColor(Color.primaryContainer)
                        .font(AppTypography.titleMedium)
                        .padding(.vertical)
                }
                
                PrimaryButton(
                    title: "Позвонить",
                    onClick: {
                        onCallPhoneClicked()
                        doCallPhone()
                    },
                    enabled: !isLoading
                )
                .disabled(isLoading)
                
                Spacer()
                
                if (isLoading) {
                    VStack {
                        ProgressView()
                            .foregroundStyle(Color.primaryContainer)
                            .frame(width: 50, height: 50)
                        Text("Ожидаем подтверждения...")
                            .font(AppTypography.titleMedium)
                            .foregroundStyle(Color.primaryContainer)
                    }
                }
                Spacer()
            }
            .padding()
            .background(
                Color.white
                    .clipShape(RoundedCorner(radius: 20, corners: [.topLeft, .topRight]))
            )
        }
        .edgesIgnoringSafeArea([.bottom, .top])
        .frame(maxWidth: .infinity)
        .navigationBarBackButtonHidden(true)
        .background(Color.darkCosmicBlue)
        .onChange(of: scenePhase) { newPhase in
            switch newPhase {
            case .active:
                // Приложение вернулось в foreground
                onAppBecameActive()
            case .background:
                // Ушло в background
                break
            case .inactive:
                break
            @unknown default:
                break
            }
        }
    }
    
    private func doCallPhone() {
        let formattedNumber = "+" + callPhone.replacingOccurrences(of: " ", with: "")
        if let url = URL(string: "tel://\(formattedNumber)"),
            UIApplication.shared.canOpenURL(url) {
            UIApplication.shared.open(url)
        }
    }
}
