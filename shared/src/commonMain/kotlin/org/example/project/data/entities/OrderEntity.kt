package org.example.project.data.entities

import kotlinx.serialization.Serializable
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.OrderStatus

@Serializable
data class OrderEntity(
    val id: Long,
    val status: OrderStatus,
    val deliveryType: DeliveryType,
    val deliveryAddress: String?,
    val items: List<OrderItemEntity>,
    val deliveryPrice: Double,
    val totalAmount: Double,
    val comment: String?,
)