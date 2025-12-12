package org.example.project.features.mapper

import org.example.project.domain.models.OrderModel
import org.example.project.domain.models.OrderStatus
import org.example.project.features.home.OrderUIModel

class OrderUIModelMapper {
    fun toUIModel(model: OrderModel): OrderUIModel {
        return OrderUIModel(
            id = model.id,
            number = model.id.toString(),
            status = OrderStatus.getTitle(model.status),
            amount = model.totalAmount.toInt()
        )
    }
}