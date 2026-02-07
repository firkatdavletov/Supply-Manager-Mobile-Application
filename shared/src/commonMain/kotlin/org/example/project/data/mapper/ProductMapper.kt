package org.example.project.data.mapper

import org.example.project.data.entities.ProductEntity
import org.example.project.domain.models.ProductModel

class ProductMapper {
    fun toModel(entity: ProductEntity) =
        ProductModel(
            id = entity.id,
            title = entity.title,
            imageUrl = entity.imageUrl,
            description = entity.description,
            price = entity.price,
            categoryId = entity.categoryId,
            count = 0,
        )

    fun toModel(entities: List<ProductEntity>) = entities.map { toModel(it) }
}