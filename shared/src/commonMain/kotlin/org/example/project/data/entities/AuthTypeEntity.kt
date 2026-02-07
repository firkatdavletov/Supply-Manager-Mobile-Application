package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class AuthTypeEntity(
    val key: String,
    val title: String,
)