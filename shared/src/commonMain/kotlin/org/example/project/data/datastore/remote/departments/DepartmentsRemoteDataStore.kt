package org.example.project.data.datastore.remote.departments

import kotlinx.coroutines.flow.Flow
import org.example.project.data.entities.DepartmentEntity

interface DepartmentsRemoteDataStore {
    fun loadDepartments(): Flow<List<DepartmentEntity>>
}