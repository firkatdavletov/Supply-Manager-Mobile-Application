package org.example.project.features.dialogs.deleteUserDialog

data class DeleteUserDialogCallbacks(
    val onDismiss: () -> Unit,
    val onSuccess: () -> Unit,
)