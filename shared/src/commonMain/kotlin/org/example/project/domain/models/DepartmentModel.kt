package org.example.project.domain.models

data class DepartmentModel(
    val id: Int,
    val name: String,
    val city: CityModel,
    val latitude: Double,
    val longitude: Double,
    val currentWorkingHours: WorkingHoursModel?,
    val isWorkingNow: Boolean,
    val selected: Boolean = false
)
