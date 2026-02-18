package org.example.project.domain.models

import kotlinx.serialization.Serializable
import org.example.project.domain.models.OrderStatus

data class OrderPreviewModel(
    val id: Long,
    val totalAmount: Long,
    val status: OrderStatus,
    val customerName: String,
    val companyName: String?,
    val deliveryTime: String,
)