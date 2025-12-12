package org.example.project.domain.models

data class PaymentModel(
    val success: Boolean,
    val qrLink: String?,
)
