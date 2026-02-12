package org.example.project.features.authorization.signInComponent

data class SignInCallbacks(
    val onBack: () -> Unit,
    val navigateToHome: () -> Unit,
)
