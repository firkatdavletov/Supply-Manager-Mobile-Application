package org.example.project.features.dialogs.logoutUserDialog

import org.example.project.features.base.Reducer

class LogoutUserDialogReducer : Reducer<LogoutUserDialogViewState, LogoutUserDialogViewEvent, LogoutUserDialogViewEffect> {
    override fun reduce(
        state: LogoutUserDialogViewState,
        event: LogoutUserDialogViewEvent,
    ): LogoutUserDialogViewState {
        return when (event) {
            LogoutUserDialogViewEvent.OnConfirm -> state
            LogoutUserDialogViewEvent.OnDismiss -> state
            is LogoutUserDialogViewEvent.OnError -> state.copy(isLoading = false)
            LogoutUserDialogViewEvent.OnLoading -> state.copy(isLoading = true)
            is LogoutUserDialogViewEvent.OnThrowError -> state.copy(isLoading = false)
        }
    }

    override fun handleEvent(event: LogoutUserDialogViewEvent): LogoutUserDialogViewEffect? {
        TODO("Not yet implemented")
    }
}