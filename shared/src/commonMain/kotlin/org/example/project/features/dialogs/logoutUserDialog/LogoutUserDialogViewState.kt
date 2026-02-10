package org.example.project.features.dialogs.logoutUserDialog

import org.example.project.features.base.Reducer

data class LogoutUserDialogViewState(
    val isLoading: Boolean,
) : Reducer.ViewState