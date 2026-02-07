package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class CartItemEntity(
    val productId: Long,
    val title: String,
    val quantity: Int,
    val price: Long,
)