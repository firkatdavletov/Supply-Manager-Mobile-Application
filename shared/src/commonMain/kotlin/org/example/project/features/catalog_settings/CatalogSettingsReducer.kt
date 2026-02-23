package org.example.project.features.catalog_settings

import org.example.project.features.base.Reducer

class CatalogSettingsReducer :
    Reducer<CatalogSettingsViewState, CatalogSettingsViewEvent, CatalogSettingsViewEffect> {
    override fun reduce(
        state: CatalogSettingsViewState,
        event: CatalogSettingsViewEvent,
    ): CatalogSettingsViewState {
        return when (event) {
            is CatalogSettingsViewEvent.OnMenuItemClicked -> {
                state.copy(
                    selectedItem = event.item,
                )
            }

            CatalogSettingsViewEvent.OnBackClicked -> {
                state
            }
        }
    }

    override fun handleEvent(event: CatalogSettingsViewEvent): CatalogSettingsViewEffect? {
        return null
    }
}
