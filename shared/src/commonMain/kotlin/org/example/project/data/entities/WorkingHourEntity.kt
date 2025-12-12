package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class WorkingHourEntity(
    val dayOfWeek: String,
    val openTime: String,
    val closeTime: String,
)