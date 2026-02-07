package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class CategoryEntity(
    val id: Long,
    val parentCategory: Long?,
    val title: String,
    val imageUrl: String?,
    val products: List<ProductEntity>,
    val children: List<CategoryEntity>,
)