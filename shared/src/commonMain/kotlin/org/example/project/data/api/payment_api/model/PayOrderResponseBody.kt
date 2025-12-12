package org.example.project.data.api.payment_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.OrderEntity

@Serializable
data class PayOrderResponseBody(
    val order: OrderEntity,
)