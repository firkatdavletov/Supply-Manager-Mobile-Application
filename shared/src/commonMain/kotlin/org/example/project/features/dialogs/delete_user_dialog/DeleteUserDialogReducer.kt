package org.example.project.features.dialogs.delete_user_dialog

import org.example.project.features.base.Reducer

class DeleteUserDialogReducer : Reducer<DeleteUserDialogViewState, DeleteUserDialogViewEvent, DeleteUserDialogViewEffect> {
    override fun reduce(
        state: DeleteUserDialogViewState,
        event: DeleteUserDialogViewEvent
    ): DeleteUserDialogViewState {
        return when (event) {
            DeleteUserDialogViewEvent.OnConfirm -> state
            DeleteUserDialogViewEvent.OnDismiss -> state
            is DeleteUserDialogViewEvent.OnError -> state.copy(isLoading = false)
            DeleteUserDialogViewEvent.OnLoading -> state.copy(isLoading = true)
            is DeleteUserDialogViewEvent.OnThrowError -> state.copy(isLoading = false)
        }
    }

    override fun handleEvent(event: DeleteUserDialogViewEvent): DeleteUserDialogViewEffect? {
        TODO("Not yet implemented")
    }
}