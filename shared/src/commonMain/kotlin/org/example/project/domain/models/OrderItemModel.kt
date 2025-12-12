package org.example.project.domain.models

data class OrderItemModel(
    val productId: Long,
    val name: String,
    val quantity: Int,
    val price: Float,
)