package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class AddressEntity(
    val street: String,
    val house: String,
    val entrance: Int?,
    val flat: String?,
    val intercome: String?,
    val comment: String?,
    val city: CityEntity,
    val latitude: Double,
    val longitude: Double,
)