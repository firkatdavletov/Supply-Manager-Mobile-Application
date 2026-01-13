package org.example.project.data.api.auth_api.model

import kotlinx.serialization.Serializable

@Serializable
data class VerifyPhoneNumberRequestBody(
    val phone: String,
    val type: String
)