package org.example.project.data.api.payment_api.model

import kotlinx.serialization.Serializable

@Serializable
data class PayOrderRequestBody(
    val paymentType: String,
    val token: String?,
    val cryptogram: String?,
    val amount: Double,
)
