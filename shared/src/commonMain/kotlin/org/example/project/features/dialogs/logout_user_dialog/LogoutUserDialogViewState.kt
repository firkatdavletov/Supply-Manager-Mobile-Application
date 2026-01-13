package org.example.project.features.dialogs.logout_user_dialog

import org.example.project.features.base.Reducer

data class LogoutUserDialogViewState(
    val isLoading: Boolean
) : Reducer.ViewState
