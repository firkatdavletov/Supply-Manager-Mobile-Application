package org.example.project.data.api.payment_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.PaymentTypeEntity

@Serializable
data class GetPaymentTypesResponseBody(
    val paymentTypes: List<PaymentTypeEntity>
)
