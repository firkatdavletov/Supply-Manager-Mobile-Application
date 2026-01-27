//
//  DeleteUserDialog.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 13/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct LogoutUserDialog: View {
    let component: LogoutUserComponent
    @StateValue private var state: LogoutUserDialogViewState
    
    init(component: LogoutUserComponent) {
        self.component = component
        _state = StateValue(self.component.state)
    }
    
    
    var body: some View {
        VStack {
            Text("Выйти из профиля?")
                .font(AppTypography.headlineLarge.bold())
                .foregroundStyle(Color.onBackground)
                .padding(.top, 32)
                .padding(.horizontal)
            Spacer()
            SecondaryButton(
                title: "Выйти",
                onClick: {
                    component.onEvent(event: LogoutUserDialogViewEventOnConfirm())
                },
                enabled: !state.isLoading
            )
            .padding(.horizontal)
            PrimaryButton(
                title: "Отмена",
                onClick: {
                    component.onEvent(event: LogoutUserDialogViewEventOnDismiss())
                },
                enabled: !state.isLoading
            )
            .padding(.horizontal)
        }
        .presentationDetents([.medium])
        .presentationDragIndicator(.visible)
        .interactiveDismissDisabled(state.isLoading)
    }
}
