package org.example.project.features.authorization.sign_in_component

import org.example.project.features.base.Reducer

data class SignInViewState(
    val authTypes: List<String>,
    val phoneNumber: String,
    val isLoading: Boolean,
    val isError: Boolean,
    val confirmEnabled: Boolean,
    val selectedAuthType: String,
    val alert: String? = null,
): Reducer.ViewState
