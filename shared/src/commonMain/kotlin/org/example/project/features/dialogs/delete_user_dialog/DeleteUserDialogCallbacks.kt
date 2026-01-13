package org.example.project.features.dialogs.delete_user_dialog

data class DeleteUserDialogCallbacks(
    val onDismiss: () -> Unit,
    val onSuccess: () -> Unit,
)
