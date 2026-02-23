package org.example.project.features.settings

import org.example.project.features.base.Reducer

sealed interface SettingsViewEffect : Reducer.ViewEffect {
    data object None : SettingsViewEffect
}
