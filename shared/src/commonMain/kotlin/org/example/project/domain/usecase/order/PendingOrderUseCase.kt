package org.example.project.domain.usecase.order

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.OrderModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.usecase.base.IOUseCase

class PendingOrderUseCase(
    private val orderRepository: OrderRepository,
) : IOUseCase<Long, ResultModel<OrderModel>>() {
    override fun execute(param: Long): Flow<ResultModel<OrderModel>> {
        return orderRepository.pendingOrder(param)
    }
}