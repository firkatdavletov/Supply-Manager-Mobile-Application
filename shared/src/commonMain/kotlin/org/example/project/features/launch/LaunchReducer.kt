package org.example.project.features.launch

import org.example.project.features.base.Reducer

class LaunchReducer: Reducer<LaunchViewState, LaunchViewEvent, LaunchViewEffect> {
    override fun reduce(
        state: LaunchViewState,
        event: LaunchViewEvent
    ): LaunchViewState {
        return when (event) {
            is LaunchViewEvent.OnError -> state.copy(
                isLoading = false,
                isError = true,
            )
            LaunchViewEvent.OnReconnect -> state.copy(
                isLoading = true,
                isError = false,
            )
        }
    }

    override fun handleEvent(event: LaunchViewEvent): LaunchViewEffect? {
        return when (event) {
            else -> null
        }
    }
}