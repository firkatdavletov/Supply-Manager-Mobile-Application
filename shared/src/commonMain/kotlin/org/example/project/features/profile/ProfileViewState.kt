package org.example.project.features.profile

import org.example.project.features.base.Reducer

data class ProfileViewState(
    val name: String,
    val phone: String,
    val isLoading: Boolean
) : Reducer.ViewState
