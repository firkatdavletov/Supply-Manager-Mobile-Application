package org.example.project.data.mapper

import org.example.project.data.entities.DepartmentEntity
import org.example.project.domain.models.DepartmentModel

class DepartmentMapper(
    private val cityMapper: CityMapper,
    private val workingHoursMapper: WorkingHoursMapper,
) {
    fun toModel(
        entity: DepartmentEntity,
    ): DepartmentModel = DepartmentModel(
        id = entity.id,
        name = entity.name,
        latitude = entity.latitude,
        longitude = entity.longitude,
        city = cityMapper.toModel(entity.city),
        workingHours = workingHoursMapper.toModel(entity.workingHours),
        currentWorkingHours = entity.currentWorkingHours?.let { workingHoursMapper.toModel(it) },
        isWorkingNow = entity.isWorkingNow,
    )

    fun toModel(entities: List<DepartmentEntity>): List<DepartmentModel> = entities.map { toModel(it) }
}