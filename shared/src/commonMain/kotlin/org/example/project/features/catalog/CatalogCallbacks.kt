package org.example.project.features.catalog

data class CatalogCallbacks(
    val onBack: () -> Unit,
    val onNavigateToCart: () -> Unit,
    val showProductCard: (Long) -> Unit,
    val onNavigateToCategory: (Int) -> Unit,
)