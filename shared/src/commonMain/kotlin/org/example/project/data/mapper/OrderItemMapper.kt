package org.example.project.data.mapper

import org.example.project.data.entities.OrderItemEntity
import org.example.project.domain.models.OrderItemModel

class OrderItemMapper {
    fun toModel(entity: OrderItemEntity) = OrderItemModel(
        productId = entity.productId,
        name = entity.name,
        quantity = entity.quantity,
        price = entity.price,
    )

    fun toModel(entities: List<OrderItemEntity>) = entities.map { toModel(it) }

    fun toEntity(model: OrderItemModel): OrderItemEntity = OrderItemEntity(
        productId = model.productId,
        name = model.name,
        quantity = model.quantity,
        price = model.price,
    )

    fun toEntity(models: List<OrderItemModel>): List<OrderItemEntity> = models.map { toEntity(it) }
}