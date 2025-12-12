package org.example.project.data.entities

import kotlinx.serialization.Serializable
import org.example.project.domain.models.DeliveryType

@Serializable
data class CartEntity(
    val items: List<CartItemEntity>,
    val deliveryType: DeliveryType,
    val deliveryAddress: AddressEntity?,
    val deliveryInfo: DeliveryInfoEntity,
    val totalPrice: Double,
    val department: DepartmentEntity,
    val comment: String?,
)