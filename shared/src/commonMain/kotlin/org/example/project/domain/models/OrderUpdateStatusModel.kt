package org.example.project.domain.models

data class OrderUpdateStatusModel(
    val orderId: Long,
    val status: OrderStatus,
)
