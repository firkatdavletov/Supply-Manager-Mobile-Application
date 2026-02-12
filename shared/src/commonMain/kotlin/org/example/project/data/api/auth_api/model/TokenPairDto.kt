package org.example.project.data.api.auth_api.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenPairDto(
    val access: String,
    val refresh: String,
)
