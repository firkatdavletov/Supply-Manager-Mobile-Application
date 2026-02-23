package org.example.project.features.settings

data class SettingsCallbacks(
    val navigateBack: () -> Unit,
    val navigateToCatalog: () -> Unit,
    val navigateToStores: () -> Unit,
    val navigateToAccounts: () -> Unit,
    val navigateToDeliveryTerms: () -> Unit,
)
