package org.example.project.features.authorization.verification_component

import org.example.project.features.base.Reducer

class VerifyReducer : Reducer<VerifyViewState, VerifyViewEvent, VerifyViewEffect> {
    override fun reduce(
        state: VerifyViewState,
        event: VerifyViewEvent
    ): VerifyViewState {
        return when (event) {
            is VerifyViewEvent.OnCodeChanged -> {
                state.copy(
                    code = event.newValue,
                    confirmEnabled = event.newValue.length == 4
                )
            }
            is VerifyViewEvent.OnError -> {
                state.copy(alert = event.message, isLoading = false)
            }
            else -> state
        }
    }

    override fun handleEvent(event: VerifyViewEvent): VerifyViewEffect? {
        TODO("Not yet implemented")
    }
}