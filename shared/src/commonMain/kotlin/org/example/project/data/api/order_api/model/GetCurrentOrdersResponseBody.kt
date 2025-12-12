package org.example.project.data.api.order_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.OrderEntity

@Serializable
data class GetCurrentOrdersResponseBody(
    val orders: List<OrderEntity>
)
