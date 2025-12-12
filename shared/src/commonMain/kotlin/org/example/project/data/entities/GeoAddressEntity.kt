package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class GeoAddressEntity(
    val city: CityEntity,
    val street: String,
    val house: String,
    val entrance: Int?,
    val deliveryInfo: DeliveryInfoEntity?,
    val deliveryTime: Int,
    val latitude: Double,
    val longitude: Double,
    val uri: String?,
)