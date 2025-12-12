package org.example.project.features.profile

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class ProfileComponent(
    componentContext: ComponentContext,
    initialState: ProfileViewState,
    reducer: ProfileViewReducer,
    snackBarManager: SnackBarManager,
) : BaseComponent<ProfileViewState, ProfileViewEvent, ProfileViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = reducer,
    snackBarManager = snackBarManager,
)