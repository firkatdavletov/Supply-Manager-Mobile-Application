package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class DepartmentEntity(
    val id: Int,
    val name: String,
    val city: CityEntity,
    val latitude: Double,
    val longitude: Double,
    val currentWorkingHours: WorkingHourEntity?,
    val isWorkingNow: Boolean,
)