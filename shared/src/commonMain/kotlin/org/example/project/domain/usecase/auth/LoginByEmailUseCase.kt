package org.example.project.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.repositories.AuthRepository
import org.example.project.domain.usecase.base.IOUseCase

class LoginByEmailUseCase(
    private val authRepository: AuthRepository,
) : IOUseCase<LoginByEmailUseCase.Params, Boolean>() {
    override fun execute(param: Params): Flow<Boolean> {
        return authRepository.loginByEmail(param.email, param.password)
    }

    data class Params(
        val email: String,
        val password: String,
    )
}
