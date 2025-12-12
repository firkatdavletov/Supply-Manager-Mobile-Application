package org.example.project.data.datastore.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.data.entities.DepartmentEntity

class DefaultDepartmentLocalDataStore: DepartmentsLocalDataStore {
    private var departments: List<DepartmentEntity> = emptyList()

    override suspend fun findDepartmentsOnce(): List<DepartmentEntity> {
        return departments
    }

    override fun findDepartments(): Flow<List<DepartmentEntity>> {
        return flow { emit(departments) }
    }

    override fun saveDepartments(departments: List<DepartmentEntity>) {
        this.departments = departments
    }
}