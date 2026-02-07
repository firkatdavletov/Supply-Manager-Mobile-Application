package org.example.project.data.mapper

import org.example.project.data.entities.OrderItemEntity
import org.example.project.domain.models.OrderItemModel

class OrderItemMapper {
    fun toModel(entity: OrderItemEntity) = OrderItemModel(
        productId = entity.productId,
        name = entity.name,
        quantity = entity.quantity,
        price = entity.price,
        totalPrice = entity.totalPrice,
        imageUrl = entity.imageUrl,
        unit = entity.unit,
    )

    fun toModel(entities: List<OrderItemEntity>) = entities.map { toModel(it) }

    fun toEntity(model: OrderItemModel): OrderItemEntity = OrderItemEntity(
        productId = model.productId,
        name = model.name,
        quantity = model.quantity,
        price = model.price,
        totalPrice = model.totalPrice,
        unit = model.unit,
        imageUrl = model.imageUrl,
    )

    fun toEntity(models: List<OrderItemModel>): List<OrderItemEntity> = models.map { toEntity(it) }
}