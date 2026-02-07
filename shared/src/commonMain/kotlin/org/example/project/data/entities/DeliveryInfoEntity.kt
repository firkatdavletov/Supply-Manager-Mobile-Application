package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class DeliveryInfoEntity(
    val deliveryPrice: Long,
    val freeDeliveryPrice: Long?,
)