package org.example.project.features.authorization.verification_component

data class VerifyCallbacks(
    val onBack: () -> Unit,
    val navigateToHome: () -> Unit,
    val navigateToPayment: () -> Unit,
)
