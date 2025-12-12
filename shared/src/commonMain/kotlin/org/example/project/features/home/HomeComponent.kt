package org.example.project.features.home

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class HomeComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    initialState: HomeViewState,
    reducer: HomeReducer,
): BaseComponent<HomeViewState, HomeViewEvent, HomeViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = reducer,
    snackBarManager = snackBarManager
)
