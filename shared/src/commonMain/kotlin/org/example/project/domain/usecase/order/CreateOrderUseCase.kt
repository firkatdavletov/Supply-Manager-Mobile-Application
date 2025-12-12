package org.example.project.domain.usecase.order

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.OrderItemModel
import org.example.project.domain.models.OrderModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.usecase.base.IOUseCase

class CreateOrderUseCase(
    private val orderRepository: OrderRepository,
) : IOUseCase<CreateOrderUseCase.Params, ResultModel<OrderModel>>() {
    override fun execute(param: Params): Flow<ResultModel<OrderModel>> {
        return orderRepository.createOrder(
            deliveryType = param.deliveryType,
            deliveryAddress = param.deliveryAddress,
            departmentId = param.departmentId,
            products = param.products,
            amount = param.amount,
            deliveryPrice = param.deliveryPrice,
            comment = param.comment,
        )
    }

    class Params(
        val deliveryType: DeliveryType,
        val deliveryAddress: AddressModel?,
        val comment: String?,
        val departmentId: Int,
        val products: List<OrderItemModel>,
        val amount: Float,
        val deliveryPrice: Float,
    )
}