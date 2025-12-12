package org.example.project.domain.models

data class AddressModel(
    val city: CityModel,
    val street: String,
    val house: String,
    val entrance: Int?,
    val flat: String? = null,
    val intercome: String? = null,
    val comment: String? = null,
    val latitude: Double,
    val longitude: Double,
)
