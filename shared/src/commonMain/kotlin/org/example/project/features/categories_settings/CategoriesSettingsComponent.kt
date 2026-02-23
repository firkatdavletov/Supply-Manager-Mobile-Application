package org.example.project.features.categories_settings

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class CategoriesSettingsComponent(
    componentContext: ComponentContext,
    initialState: CategoriesSettingsViewState,
    reducer: CategoriesSettingsReducer,
    snackBarManager: SnackBarManager? = null,
) : BaseComponent<CategoriesSettingsViewState, CategoriesSettingsViewEvent, CategoriesSettingsViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = reducer,
    snackBarManager = snackBarManager,
)
