package org.example.project.domain.usecase.user

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.ResultModel
import org.example.project.domain.models.UserModel
import org.example.project.domain.repositories.UserRepository
import org.example.project.domain.usecase.base.IOUseCase

class UpdateUserUseCase(
    private val userRepository: UserRepository,
): IOUseCase<UserModel, ResultModel<Boolean>>() {
    override fun execute(param: UserModel): Flow<ResultModel<Boolean>> {
        return userRepository.update(param)
    }
}