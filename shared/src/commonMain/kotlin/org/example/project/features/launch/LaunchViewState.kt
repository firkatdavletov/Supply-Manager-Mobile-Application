package org.example.project.features.launch

import org.example.project.features.base.Reducer

data class LaunchViewState(
    val isLoading: Boolean,
    val isError: Boolean,
): Reducer.ViewState