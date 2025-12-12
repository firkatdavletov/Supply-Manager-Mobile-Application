package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.app_introduction.AppIntroCallbacks
import org.example.project.features.app_introduction.AppIntroductionComponent
import org.example.project.features.app_introduction.DefaultAppIntroductionComponent
import org.koin.dsl.module

fun appIntroductionModule() = module {
    single<AppIntroductionComponent> { (componentContext: ComponentContext, callbacks: AppIntroCallbacks) ->
        DefaultAppIntroductionComponent(
            componentContext = componentContext,
            callbacks = callbacks
        )
    }
}