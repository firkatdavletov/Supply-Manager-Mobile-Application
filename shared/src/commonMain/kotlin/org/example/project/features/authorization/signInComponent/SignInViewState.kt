package org.example.project.features.authorization.signInComponent

import org.example.project.domain.models.AuthTypeModel
import org.example.project.features.base.Reducer

data class SignInViewState(
    val authTypes: List<AuthTypeModel>,
    val phoneNumber: String,
    val isLoading: Boolean,
    val isError: Boolean,
    val confirmEnabled: Boolean,
    val selectedAuthType: String,
) : Reducer.ViewState