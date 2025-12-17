package org.example.project.features.profile

import org.example.project.features.base.Reducer

class ProfileViewReducer : Reducer<ProfileViewState, ProfileViewEvent, ProfileViewEffect> {
    override fun reduce(
        state: ProfileViewState,
        event: ProfileViewEvent
    ): ProfileViewState {
        return when (event) {
            ProfileViewEvent.OnLogout, ProfileViewEvent.OnDelete, ProfileViewEvent.OnSave -> state.copy(
                isLoading = true
            )
            is ProfileViewEvent.OnNameChanged -> state.copy(
                name = event.name
            )
            is ProfileViewEvent.OnUserLoaded -> {
                val phone = event.user.phone.filter { it.isDigit() }
                val phoneString = if (event.user.phone.length == 11) buildString {
                    append("+${phone[0]}")
                    append("(${phone.substring(1, 4)})-${phone.substring(4, 7)}-${phone.substring(7,9)}-${phone.substring(9)}")
                } else ""
                state.copy(
                    isLoading = false,
                    name = event.user.name,
                    phone = phoneString
                )
            }
            is ProfileViewEvent.OnError -> state.copy(
                isLoading = false
            )
            else -> state
        }
    }

    override fun handleEvent(event: ProfileViewEvent): ProfileViewEffect? {
        return when (event) {
            is ProfileViewEvent.OnError -> ProfileViewEffect.ShowError(event.error)
            else -> null
        }
    }
}