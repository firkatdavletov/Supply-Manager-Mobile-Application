package org.example.project.features.settings

data class SettingsCallbacks(
    val navigateBack: () -> Unit,
    val navigateToCatalogSettings: () -> Unit,
    val navigateToStores: () -> Unit,
    val navigateToAccounts: () -> Unit,
    val navigateToDeliveryTerms: () -> Unit,
)
