package org.example.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.OrderItemModel
import org.example.project.domain.models.OrderModel
import org.example.project.domain.models.ResultModel

interface OrderRepository {
    val ordersSubject: Flow<List<OrderModel>>
//    val orderWsSubject: Flow<OrderUpdateStatusModel>
    fun getOrderById(id: Long): Flow<ResultModel<OrderModel>>
    fun getCurrentOrders(): Flow<List<OrderModel>>
    fun getOrders(): Flow<List<OrderModel>>
    fun getOrdersHistory(): Flow<List<OrderModel>>
    fun createOrder(
        deliveryType: DeliveryType,
        deliveryAddress: AddressModel?,
        departmentId: Int,
        products: List<OrderItemModel>,
        amount: Float,
        deliveryPrice: Float,
        comment: String?,
    ): Flow<ResultModel<OrderModel>>
    suspend fun clearOrders()
    suspend fun connect()
    suspend fun disconnect()
}