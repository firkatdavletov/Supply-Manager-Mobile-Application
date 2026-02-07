package org.example.project.data.api.order_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.OrderPreviewEntity
import org.example.project.data.entities.PageableEntity

@Serializable
data class GetOrdersResponseModel(
    val orders: PageableEntity<List<OrderPreviewEntity>>,
)
