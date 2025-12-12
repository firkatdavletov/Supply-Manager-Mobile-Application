//
//  AppIntrodutionView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 07.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct AppIntroductionView: View {
    let component: AppIntroductionComponent
    let pages = ["One", "Two", "Three"]
    var body: some View {
        HStack {
            Spacer()
            Button(
                action: {
                    component.onEvent(event: AppIntroductionViewEventOnContinue.shared)
                },
                label: {
                    Text("Пропустить")
                }
            )
        }
        .padding(.horizontal, 16)
        
        TabView {
            Text("Page 1")
                .font(.title)
            Text("Page 2")
                .font(.title)
            ZStack {
                Text("Page 3")
                    .font(.title)
                VStack {
                    Spacer()
                    Button(
                        action: {
                            component.onEvent(event: AppIntroductionViewEventOnContinue.shared)
                        },
                        label: {
                            Text("Продолжить")
                        }
                    )
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .frame(height: 50)
                    .cornerRadius(25)
                    .padding(.horizontal)
                    .padding(.bottom, 40)
                }
            }
        }
        .tabViewStyle(PageTabViewStyle()) // set the tab view behaviour to page
        .indexViewStyle(PageIndexViewStyle(backgroundDisplayMode: .always))
    }
}

