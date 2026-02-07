package org.example.project.data.datastore.remote.order

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import org.example.project.data.api.order_api.model.CreateOrderRequestBody
import org.example.project.data.api.order_api.model.CreateOrderResponseModel
import org.example.project.data.api.order_api.model.GetOrderByIdRequestBody
import org.example.project.data.api.order_api.model.GetOrderResponse
import org.example.project.data.api.order_api.model.GetOrdersResponseModel
import org.example.project.data.api.order_api.model.OrderStatusUpdateEntity
import org.example.project.data.entities.OrderEntity
import org.example.project.data.entities.OrderPreviewEntity

interface OrderRemoteDataStore {
    val updates: SharedFlow<OrderStatusUpdateEntity>
    fun getCurrentOrders(): Flow<List<OrderEntity>>
    fun getOrdersHistory(): Flow<List<OrderPreviewEntity>>
    suspend fun getOrders(): GetOrdersResponseModel
    suspend fun createOrder(body: CreateOrderRequestBody): CreateOrderResponseModel
    suspend fun getOrderById(body: GetOrderByIdRequestBody): GetOrderResponse
    suspend fun takeOrder(id: Long) : GetOrderResponse
    suspend fun completeOrder(id: Long) : GetOrderResponse
    suspend fun cancelOrder(id: Long) : GetOrderResponse
    suspend fun pendingOrder(id: Long) : GetOrderResponse
    suspend fun connect()
    suspend fun disconnect()
}