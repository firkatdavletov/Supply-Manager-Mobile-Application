package org.example.project.data.entities

import kotlinx.serialization.Serializable
import org.example.project.domain.models.UnitOfMeasure

@Serializable
data class OrderItemEntity(
    val productId: Long,
    val name: String,
    val quantity: Int,
    val price: Long,
    val totalPrice: Long,
    val imageUrl: String?,
    val unit: UnitOfMeasure,
)