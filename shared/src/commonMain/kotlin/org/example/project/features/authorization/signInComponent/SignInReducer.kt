package org.example.project.features.authorization.signInComponent

import org.example.project.features.base.Reducer

class SignInReducer : Reducer<SignInViewState, SignInViewEvent, SignViewEffect> {
    override fun reduce(
        state: SignInViewState,
        event: SignInViewEvent,
    ): SignInViewState {
        return when (event) {
            is SignInViewEvent.OnLoginChanged -> {
                state.copy(
                    login = event.login,
                    confirmEnabled = event.login.isNotBlank() && state.password.isNotBlank(),
                )
            }

            is SignInViewEvent.OnPasswordChanged -> {
                state.copy(
                    password = event.password,
                    confirmEnabled = state.login.isNotBlank() && event.password.isNotBlank(),
                )
            }

            SignInViewEvent.OnLoginClicked,
            SignInViewEvent.OnLoading -> {
                state.copy(
                    isLoading = true,
                )
            }

            is SignInViewEvent.OnError, is SignInViewEvent.OnThrowError -> {
                state.copy(
                    isLoading = false,
                )
            }

            SignInViewEvent.OnBackClicked -> state
        }
    }

    override fun handleEvent(event: SignInViewEvent): SignViewEffect? {
        return when (event) {
            else -> null
        }
    }
}
