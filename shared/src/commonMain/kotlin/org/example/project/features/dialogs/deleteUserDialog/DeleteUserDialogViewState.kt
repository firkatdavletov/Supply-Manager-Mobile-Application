package org.example.project.features.dialogs.deleteUserDialog

import org.example.project.features.base.Reducer

data class DeleteUserDialogViewState(
    val isLoading: Boolean,
) : Reducer.ViewState