package org.example.project.domain.usecase.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.UserRepository
import org.example.project.domain.usecase.base.IOUseCase

class LoadUserUseCase(
    private val userRepository: UserRepository,
) : IOUseCase<Unit, ResultModel<Boolean>>() {
    override fun execute(param: Unit): Flow<ResultModel<Boolean>> {
        return userRepository.loadUser()
    }
}