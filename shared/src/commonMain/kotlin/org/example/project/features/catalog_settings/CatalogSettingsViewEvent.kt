package org.example.project.features.catalog_settings

import org.example.project.features.base.Reducer

sealed interface CatalogSettingsViewEvent : Reducer.ViewEvent {
    data object OnBackClicked : CatalogSettingsViewEvent

    data class OnMenuItemClicked(val item: CatalogSettingsMenuItem) : CatalogSettingsViewEvent
}
