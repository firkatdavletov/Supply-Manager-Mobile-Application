package org.example.project.domain.usecase.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import org.example.project.domain.models.ResultModel
import org.example.project.domain.models.UserModel
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.repositories.TokenRepository
import org.example.project.domain.repositories.UserRepository
import org.example.project.domain.usecase.base.IOUseCase

class LogoutUserUseCase(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val orderRepository: OrderRepository,
): IOUseCase<Unit, ResultModel<Boolean>>() {
    override fun execute(param: Unit): Flow<ResultModel<Boolean>> {
        return userRepository.logout()
            .onEach { resultModel ->
                if (resultModel is ResultModel.Success<Boolean>) {
                    if (resultModel.data) {
                        tokenRepository.saveAccessToken("")
                        tokenRepository.saveRefreshToken("")
                        orderRepository.clearOrders()
                        orderRepository.disconnect()
                    }
                }
            }
    }
}