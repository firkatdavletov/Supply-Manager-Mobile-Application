package org.example.project.features.authorization.signInComponent

import org.example.project.features.base.Reducer

data class SignInViewState(
    val login: String,
    val password: String,
    val isLoading: Boolean,
    val confirmEnabled: Boolean,
) : Reducer.ViewState
