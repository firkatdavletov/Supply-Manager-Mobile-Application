package org.example.project.features.dialogs.delete_user_dialog

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class DeleteUserComponent(
    componentContext: ComponentContext,
    initialState: DeleteUserDialogViewState,
    reducer: DeleteUserDialogReducer,
    snackBarManager: SnackBarManager,
) : BaseComponent<DeleteUserDialogViewState, DeleteUserDialogViewEvent, DeleteUserDialogViewEffect>(
    componentContext,
    initialState,
    reducer,
    snackBarManager,
)