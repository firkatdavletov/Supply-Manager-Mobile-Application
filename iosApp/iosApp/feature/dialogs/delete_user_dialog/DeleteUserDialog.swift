//
//  DeleteUserDialog.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 13/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct DeleteUserDialog: View {
    let component: DeleteUserComponent
    @StateValue private var state: DeleteUserDialogViewState
    
    init(component: DeleteUserComponent) {
        self.component = component
        _state = StateValue(self.component.state)
    }
    
    
    var body: some View {
        VStack {
            Text("Удалить профиль?")
                .font(AppTypography.headlineLarge.bold())
                .foregroundStyle(Color.onBackground)
                .padding(.top, 32)
                .padding(.horizontal)
            Spacer()
            PrimaryButton(
                title: "Удалить",
                onClick: {
                    component.onEvent(event: DeleteUserDialogViewEventOnConfirm())
                },
                enabled: !state.isLoading
            )
            .padding(.horizontal)
            PrimaryButton(
                title: "Отмена",
                onClick: {
                    component.onEvent(event: DeleteUserDialogViewEventOnDismiss())
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
