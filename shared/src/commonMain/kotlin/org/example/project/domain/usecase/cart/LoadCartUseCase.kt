package org.example.project.domain.usecase.cart

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.usecase.base.IOUseCase

class LoadCartUseCase(
    private val cartRepository: CartRepository,
): IOUseCase<Unit, ResultModel<Boolean>>() {
    override fun execute(param: Unit): Flow<ResultModel<Boolean>> {
        return cartRepository.loadCart()
    }
}