package org.example.project.features.profile

import org.example.project.features.base.Reducer

sealed interface ProfileViewEffect : Reducer.ViewEffect {
    data class ShowError(val message: String?) : ProfileViewEffect
}