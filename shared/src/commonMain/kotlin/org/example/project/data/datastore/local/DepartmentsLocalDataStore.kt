package org.example.project.data.datastore.local

import kotlinx.coroutines.flow.Flow
import org.example.project.data.entities.DepartmentEntity

interface DepartmentsLocalDataStore {
    suspend fun findDepartmentsOnce(): List<DepartmentEntity>
    fun findDepartments(): Flow<List<DepartmentEntity>>
    fun saveDepartments(departments: List<DepartmentEntity>)
}