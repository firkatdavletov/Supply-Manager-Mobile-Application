package org.example.project.domain.usecase.cart

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.usecase.base.IOUseCase

class AddToCartUseCase(
    private val cartRepository: CartRepository,
): IOUseCase<AddToCartUseCase.Params, ResultModel<Boolean>>() {
    override fun execute(param: Params): Flow<ResultModel<Boolean>> {
        return cartRepository.updateQuantity(param.product)
    }

    data class Params(val product: ProductModel)
}