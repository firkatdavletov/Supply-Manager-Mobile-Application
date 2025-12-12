package org.example.project.data.mapper

import org.example.project.data.entities.CategoryEntity
import org.example.project.domain.models.CategoryModel

class CategoryMapper(
    private val productMapper: ProductMapper,
) {
    fun toModel(entity: CategoryEntity) = CategoryModel(
        id = entity.id,
        parentCategoryId = entity.parentCategory,
        title = entity.title,
        imageUrl = entity.imageUrl,
        products = productMapper.toModel(entity.products),
        selected = false,
        span = entity.span,
    )

    fun toModel(entities: List<CategoryEntity>) = entities.map { toModel(it) }
}