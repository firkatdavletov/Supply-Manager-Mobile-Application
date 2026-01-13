package org.example.project.features.dialogs.delete_user_dialog

import org.example.project.features.base.Reducer

data class DeleteUserDialogViewState(
    val isLoading: Boolean
) : Reducer.ViewState
