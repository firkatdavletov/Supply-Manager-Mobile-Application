package org.example.project.data.api.departments_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.DepartmentEntity

@Serializable
data class GetDepartmentsResponse(
    val departments: List<DepartmentEntity>
)