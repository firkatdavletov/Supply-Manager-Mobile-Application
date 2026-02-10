package org.example.project.features.dialogs.logoutUserDialog

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class LogoutUserComponent(
    componentContext: ComponentContext,
    initialState: LogoutUserDialogViewState,
    reducer: LogoutUserDialogReducer,
    snackBarManager: SnackBarManager,
) : BaseComponent<LogoutUserDialogViewState, LogoutUserDialogViewEvent, LogoutUserDialogViewEffect>(
        componentContext,
        initialState,
        reducer,
        snackBarManager,
    )