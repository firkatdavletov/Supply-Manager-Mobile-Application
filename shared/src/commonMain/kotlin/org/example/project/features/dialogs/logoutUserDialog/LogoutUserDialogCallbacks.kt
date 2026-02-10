package org.example.project.features.dialogs.logoutUserDialog

data class LogoutUserDialogCallbacks(
    val onDismiss: () -> Unit,
    val onSuccess: () -> Unit,
)