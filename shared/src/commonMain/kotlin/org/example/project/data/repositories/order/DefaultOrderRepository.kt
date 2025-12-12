package org.example.project.data.repositories.order

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.example.project.data.api.order_api.model.CreateOrderRequestBody
import org.example.project.data.api.order_api.model.GetOrderByIdRequestBody
import org.example.project.data.datastore.remote.order.OrderRemoteDataStore
import org.example.project.data.mapper.AddressModelMapper
import org.example.project.data.mapper.OrderItemMapper
import org.example.project.data.mapper.OrderMapper
import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.OrderItemModel
import org.example.project.domain.models.OrderModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.OrderRepository

class DefaultOrderRepository(
    private val orderRemoteDataStore: OrderRemoteDataStore,
    private val orderMapper: OrderMapper,
    private val orderItemMapper: OrderItemMapper,
    private val addressModelMapper: AddressModelMapper,
) : OrderRepository {
    private val _ordersSubject = MutableSharedFlow<List<OrderModel>>(replay = 1)
    override val ordersSubject: Flow<List<OrderModel>> = merge(
        _ordersSubject,
        orderRemoteDataStore.updates.map { orderUpdate ->
            val orders = _ordersSubject.replayCache.firstOrNull()
            if (orders.isNullOrEmpty()) {
                emptyList()
            } else {
                val updatedOrders = orders.map { orderModel ->
                    if (orderModel.id == orderUpdate.orderId) {
                        orderModel.copy(status = orderUpdate.status)
                    } else {
                        orderModel
                    }
                }
                updatedOrders
            }
        }
    )

    override fun getOrderById(id: Long): Flow<ResultModel<OrderModel>> {
        return flow {
            emit(ResultModel.Loading)
            val requestBody = GetOrderByIdRequestBody(id)
            val response = orderRemoteDataStore.getOrderById(requestBody)

            if (response.success && response.order != null) {
                val order = orderMapper.toModel(response.order)
                emit(ResultModel.Success(order))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }

    override fun getCurrentOrders(): Flow<List<OrderModel>> {
        return orderRemoteDataStore.getCurrentOrders()
            .onEach {
                _ordersSubject.emit(orderMapper.toModel(it))
            }
            .map { orderMapper.toModel(it) }
    }

    override fun getOrders(): Flow<List<OrderModel>> {
        return orderRemoteDataStore.getOrders().map { orderMapper.toModel(it)}
    }

    override fun getOrdersHistory(): Flow<List<OrderModel>> {
        return orderRemoteDataStore.getOrdersHistory().map { orderMapper.toModel(it)}
    }

    override fun createOrder(
        deliveryType: DeliveryType,
        deliveryAddress: AddressModel?,
        departmentId: Int,
        products: List<OrderItemModel>,
        amount: Float,
        deliveryPrice: Float,
        comment: String?,
    ): Flow<ResultModel<OrderModel>> {
        return flow {
            val requestBody = CreateOrderRequestBody(
                deliveryType = deliveryType,
                deliveryAddress = deliveryAddress?.let { addressModelMapper.toEntity(it) },
                departmentId = departmentId,
                products = orderItemMapper.toEntity(products),
                deliveryPrice = deliveryPrice,
                amount = amount,
                comment = comment,
            )
            val response = orderRemoteDataStore.createOrder(requestBody)

            if (response.success && response.order != null) {
                val orderModel = orderMapper.toModel(response.order)
                val orders = _ordersSubject.replayCache.firstOrNull()

                if (orders.isNullOrEmpty()) {
                    _ordersSubject.emit(listOf(orderModel))
                } else {
                    _ordersSubject.emit(orders + orderModel)
                }

                emit(ResultModel.Success(orderModel))
            } else {
                emit(ResultModel.Error("Ошибка обработки заказа"))
            }
        }
    }

    override suspend fun clearOrders() {
        _ordersSubject.emit(emptyList())
    }

    override suspend fun connect() {
        orderRemoteDataStore.connect()
    }

    override suspend fun disconnect() {
        orderRemoteDataStore.disconnect()
    }
}