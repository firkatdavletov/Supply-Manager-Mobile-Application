package org.example.project.features.launch

import org.example.project.features.base.Reducer

sealed interface LaunchViewEffect: Reducer.ViewEffect {
    data class ShowError(val message: String) : LaunchViewEffect
}