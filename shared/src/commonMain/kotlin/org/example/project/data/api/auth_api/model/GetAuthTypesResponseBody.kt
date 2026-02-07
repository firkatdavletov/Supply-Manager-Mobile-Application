package org.example.project.data.api.auth_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.api.ResponseModel
import org.example.project.data.entities.AuthTypeEntity

@Serializable
data class GetAuthTypesResponseBody(
    val types: List<AuthTypeEntity>?,
    override val success: Boolean,
    override val error: String?,
    override val code: Int?,
) : ResponseModel