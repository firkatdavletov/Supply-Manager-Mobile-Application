package org.example.project.data.api.auth_api.model

import kotlinx.serialization.Serializable

@Serializable
data class VerificationRequestBody(
    val phone: String,
    val code: String,
)