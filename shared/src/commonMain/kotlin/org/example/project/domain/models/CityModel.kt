package org.example.project.domain.models

data class CityModel(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val subCities: List<CityModel>
)
