package org.example.project.features.products_settings

data class ProductsSettingsCallbacks(
    val navigateBack: () -> Unit,
    val navigateToAddProduct: () -> Unit,
    val navigateToEditProduct: (Long) -> Unit,
)
