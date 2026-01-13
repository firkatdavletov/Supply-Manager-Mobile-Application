package org.example.project.features.dialogs.logout_user_dialog

import org.example.project.features.base.Reducer

sealed interface LogoutUserDialogViewEvent : Reducer.ViewEvent {
    data object OnConfirm : LogoutUserDialogViewEvent
    data object OnDismiss : LogoutUserDialogViewEvent
    data class OnError(val error: String) : LogoutUserDialogViewEvent
    data class OnThrowError(val throwable: Throwable) : LogoutUserDialogViewEvent
    data object OnLoading : LogoutUserDialogViewEvent
}