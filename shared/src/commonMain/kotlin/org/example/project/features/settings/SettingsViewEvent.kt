package org.example.project.features.settings

import org.example.project.features.base.Reducer

sealed interface SettingsViewEvent : Reducer.ViewEvent {
    data object OnBackClicked : SettingsViewEvent

    data class OnMenuItemClicked(val item: SettingsMenuItem) : SettingsViewEvent
}
