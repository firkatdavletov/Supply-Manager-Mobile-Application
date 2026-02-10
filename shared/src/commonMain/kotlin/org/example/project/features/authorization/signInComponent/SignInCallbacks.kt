package org.example.project.features.authorization.signInComponent

data class SignInCallbacks(
    val onBack: () -> Unit,
    val navigateToVerify: (
        phoneNumber: String,
        authType: String,
        fromString: String?,
        checkId: String?,
        callPhone: String?,
    ) -> Unit,
    val navigateToHome: () -> Unit,
)