package org.example.project.domain.usecase.order

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.OrderModel
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetOrdersUseCase(
    private val orderRepository: OrderRepository,
) : IOUseCase<Unit, List<OrderModel>>() {
    override fun execute(param: Unit): Flow<List<OrderModel>> {
        return orderRepository.getOrders()
    }
}