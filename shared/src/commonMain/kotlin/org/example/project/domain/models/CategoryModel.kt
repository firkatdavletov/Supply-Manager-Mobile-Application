package org.example.project.domain.models

data class CategoryModel(
    val id: Long,
    val title: String,
    val imageUrl: String?,
    val parentCategoryId: Long?,
    val products: List<ProductModel>,
    val children: List<CategoryModel>,
)
