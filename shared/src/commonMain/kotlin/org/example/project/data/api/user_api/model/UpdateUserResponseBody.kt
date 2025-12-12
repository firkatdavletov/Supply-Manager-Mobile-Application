package org.example.project.data.api.user_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.api.ResponseModel
import org.example.project.data.entities.UserEntity

@Serializable
class UpdateUserResponseBody(
    val user: UserEntity?,
    override val success: Boolean,
    override val error: String?,
    override val code: Int?,
) : ResponseModel
