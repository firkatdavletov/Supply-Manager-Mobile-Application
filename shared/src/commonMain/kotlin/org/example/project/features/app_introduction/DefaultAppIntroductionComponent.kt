package org.example.project.features.app_introduction

import com.arkivanov.decompose.ComponentContext

class DefaultAppIntroductionComponent(
    componentContext: ComponentContext,
    private val callbacks: AppIntroCallbacks,
): AppIntroductionComponent(
    componentContext = componentContext,
    initialState = AppIntroductionViewState("Intro"),
    reducer = AppIntroductionReducer()
) {
    override fun onEvent(event: AppIntroductionViewEvent) {
        when (event) {
            AppIntroductionViewEvent.OnContinue -> callbacks.navigateToAuth()
        }
    }
}