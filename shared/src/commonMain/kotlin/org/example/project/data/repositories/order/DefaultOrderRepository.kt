package org.example.project.data.repositories.order

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
import org.example.project.domain.models.OrderPreviewModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.OrderRepository

class DefaultOrderRepository(
    private val orderRemoteDataStore: OrderRemoteDataStore,
    private val orderMapper: OrderMapper,
    private val orderItemMapper: OrderItemMapper,
    private val addressModelMapper: AddressModelMapper,
) : OrderRepository {
    private val _ordersSubject = MutableSharedFlow<List<OrderPreviewModel>>(replay = 1)
    override val ordersSubject: Flow<List<OrderPreviewModel>> = _ordersSubject

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

            }
            .map { orderMapper.toModel(it) }
    }

    override fun getOrders(): Flow<ResultModel<List<OrderPreviewModel>>> {
        return flow {
            emit(ResultModel.Loading)
            val response = orderRemoteDataStore.getOrders()

            val orders = orderMapper.toPreviewModel(response.orders.content)

            _ordersSubject.emit(orders)

            emit(ResultModel.Success(orders))
        }
    }

    override fun getOrdersHistory(): Flow<List<OrderPreviewModel>> {
        return orderRemoteDataStore.getOrdersHistory().map { orderMapper.toPreviewModel(it)}
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

                emit(ResultModel.Success(orderModel))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }

    override fun takeOrder(id: Long): Flow<ResultModel<OrderModel>> {
        return flow {
            emit(ResultModel.Loading)
            val response = orderRemoteDataStore.takeOrder(id)

            if (response.success && response.order != null) {
                val orderModel = orderMapper.toModel(response.order)
                val orders = _ordersSubject.replayCache.firstOrNull()
                val newOrders = orders?.map {
                    if (it.id == orderModel.id) {
                        it.copy(status = orderModel.status)
                    } else {
                        it
                    }
                } ?: emptyList()

                _ordersSubject.emit(newOrders)

                emit(ResultModel.Success(orderModel))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }

    override fun completeOrder(id: Long): Flow<ResultModel<OrderModel>> {
        return flow {
            emit(ResultModel.Loading)
            val response = orderRemoteDataStore.completeOrder(id)

            if (response.success && response.order != null) {
                val orderModel = orderMapper.toModel(response.order)
                val orders = _ordersSubject.replayCache.firstOrNull()
                val newOrders = orders?.map {
                    if (it.id == orderModel.id) {
                        it.copy(status = orderModel.status)
                    } else {
                        it
                    }
                } ?: emptyList()

                _ordersSubject.emit(newOrders)

                emit(ResultModel.Success(orderModel))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }

    override fun cancelOrder(id: Long): Flow<ResultModel<OrderModel>> {
        return flow {
            emit(ResultModel.Loading)
            val response = orderRemoteDataStore.cancelOrder(id)

            if (response.success && response.order != null) {
                val orderModel = orderMapper.toModel(response.order)
                val orders = _ordersSubject.replayCache.firstOrNull()
                val newOrders = orders?.map {
                    if (it.id == orderModel.id) {
                        it.copy(status = orderModel.status)
                    } else {
                        it
                    }
                } ?: emptyList()

                _ordersSubject.emit(newOrders)

                emit(ResultModel.Success(orderModel))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }

    override fun pendingOrder(id: Long): Flow<ResultModel<OrderModel>> {
        return flow {
            emit(ResultModel.Loading)
            val response = orderRemoteDataStore.pendingOrder(id)

            if (response.success && response.order != null) {
                val orderModel = orderMapper.toModel(response.order)
                val orders = _ordersSubject.replayCache.firstOrNull()
                val newOrders = orders?.map {
                    if (it.id == orderModel.id) {
                        it.copy(status = orderModel.status)
                    } else {
                        it
                    }
                } ?: emptyList()

                _ordersSubject.emit(newOrders)

                emit(ResultModel.Success(orderModel))
            } else {
                emit(ResultModel.Error(response.error, response.code))
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