package org.example.project.features.catalog_settings

import com.arkivanov.decompose.ComponentContext

class DefaultCatalogSettingsComponent(
    componentContext: ComponentContext,
    private val callbacks: CatalogSettingsCallbacks,
) : CatalogSettingsComponent(
        componentContext = componentContext,
        initialState = CatalogSettingsViewState(),
        reducer = CatalogSettingsReducer(),
    ) {

    override fun onEvent(event: CatalogSettingsViewEvent) {
        when (event) {
            CatalogSettingsViewEvent.OnBackClicked -> {
                callbacks.navigateBack()
            }

            is CatalogSettingsViewEvent.OnMenuItemClicked -> {
                reduce(event)
                when (event.item) {
                    CatalogSettingsMenuItem.CATEGORY_CARDS -> callbacks.navigateToCategoryCards()
                    CatalogSettingsMenuItem.PRODUCT_CARDS -> callbacks.navigateToProductCards()
                    CatalogSettingsMenuItem.CSV_IMPORT -> callbacks.navigateToCsvImport()
                }
            }
        }
    }
}
