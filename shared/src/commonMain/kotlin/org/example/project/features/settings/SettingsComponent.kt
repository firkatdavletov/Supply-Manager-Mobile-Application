package org.example.project.features.settings

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.base.BaseComponent

abstract class SettingsComponent(
    componentContext: ComponentContext,
    initialState: SettingsViewState,
    reducer: SettingsReducer,
) : BaseComponent<SettingsViewState, SettingsViewEvent, SettingsViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = reducer,
)
