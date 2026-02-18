package org.example.project.data.entities

import kotlinx.serialization.Serializable
import org.example.project.domain.models.OrderStatus

@Serializable
data class OrderPreviewEntity(
    val id: Long,
    val totalAmount: Long,
    val status: OrderStatus,
    val customerName: String,
    val companyName: String?,
    val deliveryTime: String,
)