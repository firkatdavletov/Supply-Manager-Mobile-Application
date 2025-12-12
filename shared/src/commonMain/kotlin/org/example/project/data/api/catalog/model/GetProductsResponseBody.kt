package org.example.project.data.api.catalog.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.CategoryEntity
import org.example.project.data.entities.ProductEntity

@Serializable
data class GetProductsResponseBody(
    val category: CategoryEntity,
    val products: List<ProductEntity>
)