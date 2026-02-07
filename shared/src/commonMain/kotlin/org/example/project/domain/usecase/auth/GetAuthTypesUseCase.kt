package org.example.project.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.AuthTypeModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.AuthRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetAuthTypesUseCase(
    private val authRepository: AuthRepository,
) : IOUseCase<Unit, ResultModel<List<AuthTypeModel>>>() {
    override fun execute(param: Unit): Flow<ResultModel<List<AuthTypeModel>>> {
        return authRepository.getAuthTypes()
    }
}