package org.example.project.features.authorization.sign_in_component

import org.example.project.features.base.Reducer

class SignInReducer: Reducer<SignInViewState, SignInViewEvent, SignViewEffect> {
    override fun reduce(
        state: SignInViewState,
        event: SignInViewEvent
    ): SignInViewState {
        return when (event) {
            is SignInViewEvent.AuthTypeClicked -> {
                state.copy(
                    selectedAuthType = event.authType,
                    confirmEnabled = state.phoneNumber.length == 13
                )
            }
            is SignInViewEvent.LoginClicked -> {
                state.copy(
                    isLoading = true
                )
            }
            is SignInViewEvent.OnGetAuthTypes -> {
                state.copy(
                    authTypes = event.types,
                    isLoading = false
                )
            }
            is SignInViewEvent.OnPhoneNumberChanged -> {
                state.copy(
                    phoneNumber = event.phone,
                    confirmEnabled = event.phone.length == 10 && state.selectedAuthType.isNotEmpty()
                )
            }
            is SignInViewEvent.OnShowMessage -> {
                state.copy(
                    alert = event.text,
                    isLoading = false
                )
            }
            SignInViewEvent.OnLoading -> state.copy(
                isLoading = true
            )
            is SignInViewEvent.OnError, is SignInViewEvent.OnThrowError -> state.copy(
                isLoading = false
            )
            else -> state
        }
    }

    override fun handleEvent(event: SignInViewEvent): SignViewEffect? {
        return when (event) {
            else -> null
        }
    }
}