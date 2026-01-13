package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
class TokenPairEntity(
    val access: String,
    val refresh: String,
)