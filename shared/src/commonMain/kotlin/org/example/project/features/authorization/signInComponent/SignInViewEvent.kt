package org.example.project.features.authorization.signInComponent

import org.example.project.domain.models.AuthTypeModel
import org.example.project.features.base.Reducer

sealed interface SignInViewEvent : Reducer.ViewEvent {
    data class OnGetAuthTypes(val types: List<AuthTypeModel>) : SignInViewEvent

    data class AuthTypeClicked(val authType: String) : SignInViewEvent

    data class LoginClicked(val phoneNumber: String) : SignInViewEvent

    data object OnBackClicked : SignInViewEvent

    data class OnPhoneNumberChanged(val phone: String) : SignInViewEvent

    data class OnShowMessage(val text: String?) : SignInViewEvent

    data object OnSkipClicked : SignInViewEvent

    data object OnLoading : SignInViewEvent

    data class OnError(val message: String?) : SignInViewEvent

    data class OnThrowError(val throwable: Throwable) : SignInViewEvent
}