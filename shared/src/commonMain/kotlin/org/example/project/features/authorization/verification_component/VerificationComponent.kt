package org.example.project.features.authorization.verification_component

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class VerificationComponent(
    componentContext: ComponentContext,
    initialState: VerifyViewState,
    snackBarManager: SnackBarManager,
) : BaseComponent<VerifyViewState, VerifyViewEvent, VerifyViewEffect>(
    reducer = VerifyReducer(),
    initialState = initialState,
    componentContext = componentContext,
    snackBarManager = snackBarManager
)
