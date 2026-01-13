package org.example.project.features.dialogs.logout_user_dialog

data class LogoutUserDialogCallbacks(
    val onDismiss: () -> Unit,
    val onSuccess: () -> Unit,
)
