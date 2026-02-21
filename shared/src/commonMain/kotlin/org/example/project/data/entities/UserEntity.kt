package org.example.project.data.entities

import kotlinx.serialization.Serializable
import org.example.project.domain.models.UserRole

@Serializable
data class UserEntity(
    val phone: String,
    val name: String,
    val email: String,
    val company: String?,
    val role: UserRole? = null,
)