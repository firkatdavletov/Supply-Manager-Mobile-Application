package org.example.project.features.profile

data class ProfileCallbacks(
    val navigateBack: () -> Unit,
    val showDeleteUserDialog: () -> Unit,
    val showLogoutUserDialog: () -> Unit,
)
