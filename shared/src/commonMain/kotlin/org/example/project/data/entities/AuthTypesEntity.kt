package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class AuthTypesEntity(
    val types: List<String>,
)