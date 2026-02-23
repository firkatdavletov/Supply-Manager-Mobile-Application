package org.example.project.features.products_settings

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class ProductsSettingsComponent(
    componentContext: ComponentContext,
    initialState: ProductsSettingsViewState,
    reducer: ProductsSettingsReducer,
    snackBarManager: SnackBarManager? = null,
) : BaseComponent<ProductsSettingsViewState, ProductsSettingsViewEvent, ProductsSettingsViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = reducer,
    snackBarManager = snackBarManager,
)
