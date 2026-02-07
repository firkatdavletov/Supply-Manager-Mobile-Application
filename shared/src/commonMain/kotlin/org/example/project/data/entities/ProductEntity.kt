package org.example.project.data.entities

import kotlinx.serialization.Serializable
import org.example.project.domain.models.UnitOfMeasure

@Serializable
data class ProductEntity(
    val id: Long,
    val categoryId: Long,
    val title: String,
    val description: String?,
    val price: Long,
    val unit: UnitOfMeasure,
    val imageUrl: String?,
    val displayWeight: String?,
    val countStep: Int,
)