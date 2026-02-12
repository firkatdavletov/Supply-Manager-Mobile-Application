package org.example.project.features.authorization.signInComponent

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class SignInComponent(componentContext: ComponentContext, snackBarManager: SnackBarManager) :
    BaseComponent<SignInViewState, SignInViewEvent, SignViewEffect>(
        componentContext = componentContext,
        initialState = SignInViewState(
            login = "",
            password = "",
            isLoading = false,
            confirmEnabled = false,
        ),
        reducer = SignInReducer(),
        snackBarManager = snackBarManager,
    )
