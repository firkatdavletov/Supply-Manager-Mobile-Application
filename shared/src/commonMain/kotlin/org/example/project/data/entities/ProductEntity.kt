package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class ProductEntity(
    val id: Long,
    val categoryId: Long,
    val title: String,
    val description: String?,
    val price: Float,
    val imageUrl: String?
)