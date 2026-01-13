package org.example.project.features.catalog

data class CatalogCallbacks(
    val onBack: () -> Unit,
    val onNavigateToCart: () -> Unit,
    val showProductCard: (Int) -> Unit,
)