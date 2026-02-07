package org.example.project.domain.models

data class ProductModel(
    val id: Long,
    val title: String,
    val description: String?,
    val price: Long,
    val imageUrl: String?,
    val categoryId: Long,
    val count: Int,
)