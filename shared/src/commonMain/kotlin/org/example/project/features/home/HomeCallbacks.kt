package org.example.project.features.home

data class HomeCallbacks(
    val navigateToMap: () -> Unit,
    val navigateToCatalog: () -> Unit,
    val navigateToCart: () -> Unit,
    val navigateToProfile: () -> Unit,
    val navigateToOrder: (Long) -> Unit,
    val navigateToAuthorization: () -> Unit,
)
