package org.example.project.features.cart

data class CartViewCallbacks(
    val onBackClicked: () -> Unit,
    val navigateToPayment: () -> Unit,
    val navigateToLogin: () -> Unit,
)
