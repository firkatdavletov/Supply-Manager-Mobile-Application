package org.example.project.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.repositories.AuthRepository
import org.example.project.domain.usecase.base.IOUseCase

class SendVerificationCodeUseCase(
    private val authRepository: AuthRepository,
): IOUseCase<String, Boolean>() {
    override fun execute(param: String): Flow<Boolean> {
        return authRepository.sendVerification(param)
    }
}