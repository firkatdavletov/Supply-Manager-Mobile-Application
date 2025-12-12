package org.example.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.DepartmentModel

interface DepartmentsRepository {
    fun getDepartments(): Flow<List<DepartmentModel>>
}