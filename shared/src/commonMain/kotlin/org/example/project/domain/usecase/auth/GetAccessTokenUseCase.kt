package org.example.project.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.domain.repositories.TokenRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetAccessTokenUseCase(
    private val tokenRepository: TokenRepository,
): IOUseCase<Unit, String?>() {
    override fun execute(param: Unit): Flow<String?> {
        return flow {
            val token = tokenRepository.getAccessToken()
            emit(token)
        }
    }
}