package org.example.project.data.api.auth_api.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginByEmailRequestBody(
    val email: String,
    val password: String,
)
