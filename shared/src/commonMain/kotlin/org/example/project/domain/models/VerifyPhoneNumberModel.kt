package org.example.project.domain.models

data class VerifyPhoneNumberModel(
    val success: Boolean,
    val checkId: String?,
    val callPhone: String?
)
