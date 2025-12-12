package org.example.project.features.authorization.sign_in_component

import org.example.project.features.base.Reducer

sealed interface SignInViewEvent : Reducer.ViewEvent {
    data class OnGetAuthTypes(val types: List<String>) : SignInViewEvent
    data class AuthTypeClicked(val authType: String) : SignInViewEvent
    data class LoginClicked(val phoneNumber: String) : SignInViewEvent
    data object OnBackClicked : SignInViewEvent
    data class OnPhoneNumberChanged(val phone: String): SignInViewEvent
    data class OnShowMessage(val text: String?): SignInViewEvent
    data object OnSkipClicked: SignInViewEvent
    data object OnLoading: SignInViewEvent
    data class OnError(val message: String?) : SignInViewEvent
    data class OnThrowError(val throwable: Throwable) : SignInViewEvent
}