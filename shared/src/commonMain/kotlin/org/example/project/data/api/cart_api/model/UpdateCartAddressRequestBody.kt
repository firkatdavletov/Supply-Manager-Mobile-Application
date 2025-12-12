package org.example.project.data.api.cart_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.AddressEntity
import org.example.project.data.entities.DeliveryInfoEntity
import org.example.project.domain.models.DeliveryInfoModel
import org.example.project.domain.models.DeliveryType

@Serializable
data class UpdateCartAddressRequestBody(
    val deliveryType: DeliveryType,
    val deliveryAddress: AddressEntity?,
    val departmentId: Int?,
    val deliveryInfo: DeliveryInfoEntity,
    val comment: String?,
)