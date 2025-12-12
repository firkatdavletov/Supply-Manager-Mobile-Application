package org.example.project.features.payment

data class PaymentCallbacks(
    val navigateBack: () -> Unit,
    val navigateToOrder: (orderId: Long) -> Unit,
    val navigateToMap: () -> Unit,
)
