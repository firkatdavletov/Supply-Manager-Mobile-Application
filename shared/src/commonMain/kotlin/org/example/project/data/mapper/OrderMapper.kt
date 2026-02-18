package org.example.project.data.mapper

import org.example.project.data.entities.OrderEntity
import org.example.project.data.entities.OrderPreviewEntity
import org.example.project.domain.models.OrderModel
import org.example.project.domain.models.OrderPreviewModel
import org.example.project.domain.utils.DateUtility

class OrderMapper(
    private val orderItemMapper: OrderItemMapper,
    private val userMapper: UserMapper,
) {
    fun toModel(entity: OrderEntity): OrderModel {
        val items = orderItemMapper.toModel(entity.items)
        return OrderModel(
            id = entity.id,
            user = userMapper.toModel(entity.user),
            status = entity.status,
            items = items,
            deliveryPrice = entity.deliveryPrice,
            totalAmount = entity.totalAmount,
            deliveryType = entity.deliveryType,
            deliveryAddress = entity.deliveryAddress,
            deliveryTime = entity.deliveryTime,
            comment = entity.comment,
            created = entity.created,
            modified = entity.modified,
        )
    }

    fun toPreviewModel(entity: OrderPreviewEntity) =
        OrderPreviewModel(
            id = entity.id,
            totalAmount = entity.totalAmount,
            status = entity.status,
            customerName = entity.customerName,
            companyName = entity.companyName,
            deliveryTime = entity.deliveryTime,
        )

    fun toPreviewModel(entities: List<OrderPreviewEntity>) = entities.map { toPreviewModel(it) }

    fun toModel(entities: List<OrderEntity>) =
        entities.map {
            toModel(it)
        }
}