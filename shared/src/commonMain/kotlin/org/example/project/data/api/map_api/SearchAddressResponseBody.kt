package org.example.project.data.api.map_api

import kotlinx.serialization.Serializable
import org.example.project.data.api.ResponseModel
import org.example.project.data.entities.GeoAddressEntity

@Serializable
data class SearchAddressResponseBody(
    val addresses: List<GeoAddressEntity>?,
    override val success: Boolean,
    override val error: String?,
    override val code: Int?
) : ResponseModel
