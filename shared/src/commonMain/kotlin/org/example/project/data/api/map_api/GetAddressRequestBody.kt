package org.example.project.data.api.map_api

import kotlinx.serialization.Serializable
import org.example.project.data.api.ResponseModel
import org.example.project.data.entities.GeoAddressEntity

@Serializable
class GetAddressResponseBody(
    val address: GeoAddressEntity?,
    override val success: Boolean,
    override val error: String?,
    override val code: Int?
) : ResponseModel