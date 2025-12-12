package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemEntity(
    val productId: Long,
    val name: String,
    val quantity: Int,
    val price: Float,
)