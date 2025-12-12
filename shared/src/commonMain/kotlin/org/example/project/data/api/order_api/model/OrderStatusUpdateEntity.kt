package org.example.project.data.api.order_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.OrderEntity
import org.example.project.domain.models.OrderStatus

@Serializable
data class OrderStatusUpdateEntity(
    val orderId: Long,
    val status: OrderStatus
)