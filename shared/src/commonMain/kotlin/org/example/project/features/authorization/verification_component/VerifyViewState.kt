package org.example.project.features.authorization.verification_component

import org.example.project.features.base.Reducer

data class VerifyViewState(
    val isLoading: Boolean,
    val phoneNumber: String,
    val code: String,
    val confirmEnabled: Boolean,
    val alert: String? = null,
): Reducer.ViewState
