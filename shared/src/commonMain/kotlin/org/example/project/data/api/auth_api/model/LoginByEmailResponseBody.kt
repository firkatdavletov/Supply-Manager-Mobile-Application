package org.example.project.data.api.auth_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.api.ResponseModel

@Serializable
class LoginByEmailResponseBody(
    val tokens: TokenPairDto?,
    override val success: Boolean,
    override val error: String?,
    override val code: Int?,
) : ResponseModel