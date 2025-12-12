package org.example.project.data.repositories.departments

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.datastore.local.DepartmentsLocalDataStore
import org.example.project.data.datastore.remote.departments.DepartmentsRemoteDataStore
import org.example.project.data.mapper.DepartmentMapper
import org.example.project.domain.models.DepartmentModel
import org.example.project.domain.repositories.DepartmentsRepository

class DefaultDepartmentRepository(
    private val remoteStore: DepartmentsRemoteDataStore,
    private val localStore: DepartmentsLocalDataStore,
    private val departmentMapper: DepartmentMapper,
): DepartmentsRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getDepartments(): Flow<List<DepartmentModel>> {
        return remoteStore.loadDepartments().map {
            departmentMapper.toModel(it)
        }
    }
}