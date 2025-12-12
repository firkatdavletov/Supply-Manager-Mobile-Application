package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class CityEntity(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val subCities: List<CityEntity>
)
