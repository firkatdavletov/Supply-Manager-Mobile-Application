package org.example.project.domain.usecase.departments

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.DepartmentModel
import org.example.project.domain.repositories.DepartmentsRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetDepartmentsUseCase(
    private val repository: DepartmentsRepository
): IOUseCase<Unit, List<DepartmentModel>>() {
    override fun execute(param: Unit): Flow<List<DepartmentModel>> {
        return repository.getDepartments()
    }
}