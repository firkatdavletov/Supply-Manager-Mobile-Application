package org.example.project.features.launch

data class LaunchNavigationCallbacks(
    val navigateToHome: () -> Unit,
    val navigateToSignIn: () -> Unit
)