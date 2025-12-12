package org.example.project.features.authorization

data class AuthNavCallbacks(
    val navigateToHome: () -> Unit,
    val navigateToPayment: () -> Unit,
)
