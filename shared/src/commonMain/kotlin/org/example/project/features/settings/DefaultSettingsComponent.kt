package org.example.project.features.settings

import com.arkivanov.decompose.ComponentContext

class DefaultSettingsComponent(
    componentContext: ComponentContext,
    private val callbacks: SettingsCallbacks,
) : SettingsComponent(
        componentContext = componentContext,
        initialState = SettingsViewState(),
        reducer = SettingsReducer(),
    ) {

    override fun onEvent(event: SettingsViewEvent) {
        when (event) {
            SettingsViewEvent.OnBackClicked -> {
                callbacks.navigateBack()
            }

            is SettingsViewEvent.OnMenuItemClicked -> {
                reduce(event)
                when (event.item) {
                    SettingsMenuItem.CATALOG -> callbacks.navigateToCatalogSettings()
                    SettingsMenuItem.STORES -> callbacks.navigateToStores()
                    SettingsMenuItem.ACCOUNTS -> callbacks.navigateToAccounts()
                    SettingsMenuItem.DELIVERY_TERMS -> callbacks.navigateToDeliveryTerms()
                }
            }
        }
    }
}
