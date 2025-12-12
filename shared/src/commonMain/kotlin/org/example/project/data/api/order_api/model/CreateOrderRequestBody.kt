package org.example.project.data.api.order_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.AddressEntity
import org.example.project.data.entities.OrderItemEntity
import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.DeliveryType

@Serializable
class CreateOrderRequestBody(
    val deliveryType: DeliveryType,
    val deliveryAddress: AddressEntity?,
    val products: List<OrderItemEntity>,
    val departmentId: Int,
    val amount: Float,
    val deliveryPrice: Float,
    val comment: String?,
)