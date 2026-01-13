package org.example.project.data.api.auth_api.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckSmsCodeRequestBody(
    val phone: String,
    val code: String,
)