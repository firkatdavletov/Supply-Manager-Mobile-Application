package org.example.project.data.api.catalog.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.CategoryEntity

@Serializable
data class GetCategoriesResponseBody(
    val categories: List<CategoryEntity>
)