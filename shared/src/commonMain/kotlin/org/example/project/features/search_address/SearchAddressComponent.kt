package org.example.project.features.search_address

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class SearchAddressComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    initialState: SearchAddressViewState,
    reducer: SearchAddressReducer,
) : BaseComponent<SearchAddressViewState, SearchAddressViewEvent, SearchAddressViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = reducer,
    snackBarManager = snackBarManager,
)
