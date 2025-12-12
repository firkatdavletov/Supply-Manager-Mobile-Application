package org.example.project.data.api.order_api.model

import org.example.project.data.entities.OrderEntity

data class GetOrdersResponseModel(
    val orders: List<OrderEntity>,
)
