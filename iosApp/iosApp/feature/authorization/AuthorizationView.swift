//
//  AuthorizationView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct AuthorizationView: View {
    let root: AuthorizationComponent
    var body: some View {
        StackView(
            stackValue: StateValue(root.childStack),
            getTitle: { a in
                return ""
            },
            onBack: root.onBackClicked,
            childContent: { child in
                switch child {
                case let child as AuthorizationComponentChild.SignInChild: SignInView(component: child.component)
                case let child as AuthorizationComponentChild.VerificationChild: VerificationView(component: child.component)
                default: EmptyView()
                }
            }
        )
    }
}
