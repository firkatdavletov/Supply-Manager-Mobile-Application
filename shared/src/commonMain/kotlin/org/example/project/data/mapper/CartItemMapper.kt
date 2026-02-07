package org.example.project.data.mapper

import org.example.project.data.entities.CartItemEntity
import org.example.project.domain.models.CartItemModel

class CartItemMapper {
    fun toModel(entity: CartItemEntity) =
        CartItemModel(
            productId = entity.productId,
            title = entity.title,
            quantity = entity.quantity,
            price = entity.price,
        )

    fun toModel(entities: List<CartItemEntity>) = entities.map { toModel(it) }
}