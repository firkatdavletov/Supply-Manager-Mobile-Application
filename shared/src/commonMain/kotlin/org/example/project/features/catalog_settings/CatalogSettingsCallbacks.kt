package org.example.project.features.catalog_settings

data class CatalogSettingsCallbacks(
    val navigateBack: () -> Unit,
    val navigateToCategoryCards: () -> Unit,
    val navigateToProductCards: () -> Unit,
    val navigateToCsvImport: () -> Unit,
)
