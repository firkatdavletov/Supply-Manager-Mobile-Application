package org.example.project.features.map

data class MapCallbacks(
    val navigateBack: () -> Unit,
    val navigateToSearchAddress: (fromScreen: String?) -> Unit,
    val navigateToHome: () -> Unit,
    val navigateToPayment: () -> Unit,
)
