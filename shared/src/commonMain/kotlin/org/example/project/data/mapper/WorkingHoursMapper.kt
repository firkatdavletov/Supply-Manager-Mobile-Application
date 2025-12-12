package org.example.project.data.mapper

import org.example.project.data.entities.WorkingHourEntity
import org.example.project.domain.models.WorkingHoursModel

class WorkingHoursMapper {
    fun toModel(entity: WorkingHourEntity) = WorkingHoursModel(
        dayWeek = entity.dayOfWeek,
        openTime = entity.openTime,
        closeTime = entity.closeTime
    )

    fun toModel(entities: List<WorkingHourEntity>) = entities.map { toModel(it) }
}