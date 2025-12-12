package org.example.project.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.repositories.AuthRepository
import org.example.project.domain.usecase.base.IOUseCase

class VerifyCodeUseCase(
    private val authRepository: AuthRepository,
): IOUseCase<VerifyCodeUseCase.Params, Boolean>() {
    override fun execute(param: Params): Flow<Boolean> {
        return authRepository.verifyCode(param.phone, param.code)
    }

    data class Params(val phone: String, val code: String)
}