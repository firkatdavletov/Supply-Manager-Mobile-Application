package org.example.project.features.app_introduction

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.base.BaseComponent

abstract class AppIntroductionComponent(
    componentContext: ComponentContext,
    initialState: AppIntroductionViewState,
    reducer: AppIntroductionReducer,
): BaseComponent<AppIntroductionViewState, AppIntroductionViewEvent, AppIntroductionViewEffect>(
    componentContext = componentContext,
    reducer = reducer,
    initialState = initialState
)
