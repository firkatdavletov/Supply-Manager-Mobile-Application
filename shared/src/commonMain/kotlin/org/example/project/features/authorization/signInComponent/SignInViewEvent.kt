package org.example.project.features.authorization.signInComponent

import org.example.project.features.base.Reducer

sealed interface SignInViewEvent : Reducer.ViewEvent {
    data class OnLoginChanged(val login: String) : SignInViewEvent

    data class OnPasswordChanged(val password: String) : SignInViewEvent

    data object OnLoginClicked : SignInViewEvent

    data object OnBackClicked : SignInViewEvent

    data object OnLoading : SignInViewEvent

    data class OnError(val message: String?) : SignInViewEvent

    data class OnThrowError(val throwable: Throwable) : SignInViewEvent
}
