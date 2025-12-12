package org.example.project.data.api.order_api

import kotlinx.coroutines.flow.SharedFlow
import org.example.project.data.api.order_api.model.CreateOrderRequestBody
import org.example.project.data.api.order_api.model.CreateOrderResponseModel
import org.example.project.data.api.order_api.model.GetCurrentOrdersResponseBody
import org.example.project.data.api.order_api.model.GetOrderByIdRequestBody
import org.example.project.data.api.order_api.model.GetOrderByIdResponse
import org.example.project.data.api.order_api.model.GetOrdersResponseModel
import org.example.project.data.api.order_api.model.OrderStatusUpdateEntity
import org.example.project.data.entities.OrderEntity

interface OrderApi {
    val updates: SharedFlow<OrderStatusUpdateEntity>

    suspend fun getOrderById(body: GetOrderByIdRequestBody): GetOrderByIdResponse

    suspend fun currentOrders(): GetCurrentOrdersResponseBody

    suspend fun getOrders(): GetOrdersResponseModel

    suspend fun getOrdersHistory(): GetOrdersResponseModel

    suspend fun createOrder(body: CreateOrderRequestBody): CreateOrderResponseModel

    suspend fun connect()

    suspend fun disconnect()
}