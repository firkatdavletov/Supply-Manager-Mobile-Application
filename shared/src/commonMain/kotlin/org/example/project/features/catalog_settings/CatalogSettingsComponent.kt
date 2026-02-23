package org.example.project.features.catalog_settings

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.base.BaseComponent

abstract class CatalogSettingsComponent(
    componentContext: ComponentContext,
    initialState: CatalogSettingsViewState,
    reducer: CatalogSettingsReducer,
) : BaseComponent<CatalogSettingsViewState, CatalogSettingsViewEvent, CatalogSettingsViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = reducer,
)
