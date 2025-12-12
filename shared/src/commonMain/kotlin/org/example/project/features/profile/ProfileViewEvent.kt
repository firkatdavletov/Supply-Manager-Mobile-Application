package org.example.project.features.profile

import org.example.project.domain.models.UserModel
import org.example.project.features.base.Reducer

sealed interface ProfileViewEvent : Reducer.ViewEvent {
    data class OnUserLoaded(val user: UserModel) : ProfileViewEvent
    data class OnNameChanged(val name: String) : ProfileViewEvent
    data class OnError(val error: String?) : ProfileViewEvent
    data class OnThrowError(val throwable: Throwable) : ProfileViewEvent
    data object OnBackClicked : ProfileViewEvent
    data object OnLogout : ProfileViewEvent
    data object OnDelete : ProfileViewEvent
    data object OnSave : ProfileViewEvent
}