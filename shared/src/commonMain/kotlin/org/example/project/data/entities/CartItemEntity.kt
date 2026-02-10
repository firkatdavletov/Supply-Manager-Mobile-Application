package org.example.project.data.entities

import kotlinx.serialization.Serializable
import org.example.project.domain.models.UnitOfMeasure

@Serializable
data class CartItemEntity(
    val productId: Long,
    val title: String,
    val quantity: Int,
    val price: Long,
    val countStep: Int,
    val unit: UnitOfMeasure,
)