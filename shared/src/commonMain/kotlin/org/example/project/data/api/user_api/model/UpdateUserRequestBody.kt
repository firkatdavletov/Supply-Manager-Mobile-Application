package org.example.project.data.api.user_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.UserEntity

@Serializable
data class UpdateUserRequestBody(
    val user: UserEntity
)
