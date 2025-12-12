package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class PaymentModelEntity(
    val qrUrl: String? = null,
    val orderId: Long?,
    val version: String? = null
)