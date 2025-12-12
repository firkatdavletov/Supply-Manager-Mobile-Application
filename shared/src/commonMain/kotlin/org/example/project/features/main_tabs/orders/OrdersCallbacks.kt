package org.example.project.features.main_tabs.orders

data class OrdersCallbacks(
    val navigateBack: () -> Unit,
    val navigateToOrder: (Long) -> Unit,
)
