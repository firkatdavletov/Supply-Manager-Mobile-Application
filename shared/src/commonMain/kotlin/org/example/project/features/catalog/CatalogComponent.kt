package org.example.project.features.catalog

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.base.BaseComponent

abstract class CatalogComponent(
    componentContext: ComponentContext,
    initialState: CatalogViewState,
    reducer: CatalogReducer,
) : BaseComponent<CatalogViewState, CatalogViewEvent, CatalogViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = reducer,
)