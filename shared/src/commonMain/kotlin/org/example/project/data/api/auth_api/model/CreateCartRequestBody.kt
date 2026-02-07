package org.example.project.data.api.auth_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.AddressEntity
import org.example.project.data.entities.DeliveryInfoEntity
import org.example.project.domain.models.DeliveryType

@Serializable
class CreateCartRequestBody(
    val deviceId: String,
    val deliveryType: DeliveryType,
    val deliveryAddress: AddressEntity?,
    val departmentId: Int,
    val deliveryPrice: Long,
    val freeDeliveryPrice: Long?
)