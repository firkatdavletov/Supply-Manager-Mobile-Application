package org.example.project.domain.usecase.order

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.OrderPreviewModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetOrdersUseCase(
    private val orderRepository: OrderRepository,
) : IOUseCase<Unit, ResultModel<List<OrderPreviewModel>>>() {
    override fun execute(param: Unit): Flow<ResultModel<List<OrderPreviewModel>>> {
        return orderRepository.getOrders()
    }
}