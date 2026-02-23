package org.example.project.features.settings

import org.example.project.features.base.Reducer

class SettingsReducer : Reducer<SettingsViewState, SettingsViewEvent, SettingsViewEffect> {
    override fun reduce(
        state: SettingsViewState,
        event: SettingsViewEvent,
    ): SettingsViewState {
        return when (event) {
            is SettingsViewEvent.OnMenuItemClicked -> {
                state.copy(
                    selectedItem = event.item,
                )
            }

            SettingsViewEvent.OnBackClicked -> {
                state
            }
        }
    }

    override fun handleEvent(event: SettingsViewEvent): SettingsViewEffect? {
        return null
    }
}
