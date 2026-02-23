package org.example.project.features.catalog_settings

import org.example.project.features.base.Reducer

sealed interface CatalogSettingsViewEffect : Reducer.ViewEffect {
    data object None : CatalogSettingsViewEffect
}
