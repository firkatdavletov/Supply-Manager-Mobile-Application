package org.example.project.features.current_order

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.base.BaseComponent

abstract class CurrentOrderComponent(
    componentContext: ComponentContext,
    initialState: CurrentOrderViewState,
) : BaseComponent<CurrentOrderViewState, CurrentOrderViewEvent, CurrentOrderViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = CurrentOrderReducer()
)
